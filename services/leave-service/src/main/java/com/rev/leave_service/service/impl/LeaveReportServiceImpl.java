package com.rev.leave_service.service.impl;

import com.rev.leave_service.client.UserServiceClient;
import com.rev.leave_service.entity.Leave;
import com.rev.leave_service.repository.LeaveRepository;
import com.rev.leave_service.repository.LeaveTypeRepository;
import com.rev.leave_service.service.LeaveReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LeaveReportServiceImpl implements LeaveReportService {

    private final LeaveRepository leaveRepository;
    private final LeaveTypeRepository leaveTypeRepository;
    private final UserServiceClient userServiceClient;

    @Override
    public List<Map<String, Object>> getDepartmentWiseReport(Long departmentId) {
        List<Leave> allLeaves = leaveRepository.findAll();
        Map<Long, Map<String, Object>> userMap = fetchUserMap();

        Map<String, Map<String, Object>> deptStats = new HashMap<>();

        for (Leave leave : allLeaves) {
            Map<String, Object> user = userMap.get(leave.getUserId());
            if (user == null) continue;
            
            String deptName = (user.get("departmentName") != null) ? user.get("departmentName").toString() : "Other";
            
            if (departmentId != null && departmentId > 0) {
                Long userDeptId = (user.get("departmentId") != null) ? Long.valueOf(user.get("departmentId").toString()) : -1L;
                if (!departmentId.equals(userDeptId)) continue;
            }

            deptStats.putIfAbsent(deptName, createEmptyStats(deptName));
            updateStats(deptStats.get(deptName), leave);
        }

        return new ArrayList<>(deptStats.values());
    }

    @Override
    public List<Map<String, Object>> getEmployeeWiseReport(Long employeeId) {
        List<Leave> leaves = (employeeId == null || employeeId == 0) ? leaveRepository.findAll() : leaveRepository.findByUserId(employeeId);
        Map<Long, Map<String, Object>> userMap = fetchUserMap();

        Map<Long, Map<String, Object>> empStats = new HashMap<>();

        for (Leave leave : leaves) {
            Long uid = leave.getUserId();
            if (!empStats.containsKey(uid)) {
                Map<String, Object> user = userMap.get(uid);
                String name = (user != null && user.get("name") != null) ? user.get("name").toString() : "User " + uid;
                String dept = (user != null && user.get("departmentName") != null) ? user.get("departmentName").toString() : "-";
                
                Map<String, Object> stats = createEmptyStats(name);
                stats.put("department", dept);
                empStats.put(uid, stats);
            }
            updateStats(empStats.get(uid), leave);
        }

        return new ArrayList<>(empStats.values());
    }

    private Map<Long, Map<String, Object>> fetchUserMap() {
        Map<Long, Map<String, Object>> userMap = new HashMap<>();
        try {
            userServiceClient.getEmployeeDirectory().forEach(u -> {
                Long uid = Long.valueOf(u.get("id").toString());
                userMap.put(uid, u);
            });
        } catch (Exception e) {}
        return userMap;
    }

    private Map<String, Object> createEmptyStats(String name) {
        Map<String, Object> stats = new HashMap<>();
        stats.put("name", name);
        stats.put("totalLeaves", 0);
        stats.put("approvedLeaves", 0);
        stats.put("pendingLeaves", 0);
        stats.put("rejectedLeaves", 0);
        stats.put("casualLeaves", 0);
        stats.put("sickLeaves", 0);
        stats.put("paidLeaves", 0);
        return stats;
    }

    private void updateStats(Map<String, Object> stats, Leave leave) {
        stats.put("totalLeaves", (int) stats.get("totalLeaves") + 1);
        
        String status = leave.getStatus().name();
        if ("APPROVED".equals(status)) stats.put("approvedLeaves", (int) stats.get("approvedLeaves") + 1);
        else if ("PENDING".equals(status)) stats.put("pendingLeaves", (int) stats.get("pendingLeaves") + 1);
        else if ("REJECTED".equals(status)) stats.put("rejectedLeaves", (int) stats.get("rejectedLeaves") + 1);
        
        String leaveTypeKey = resolveLeaveTypeKey(leave.getLeaveTypeId());
        stats.putIfAbsent(leaveTypeKey, 0);
        stats.put(leaveTypeKey, (int) stats.get(leaveTypeKey) + 1);
    }

    private String resolveLeaveTypeKey(Long leaveTypeId) {
        try {
            return leaveTypeRepository.findById(leaveTypeId)
                    .map(type -> {
                        String name = type.getName().toLowerCase();
                        if (name.contains("casual")) return "casualLeaves";
                        if (name.contains("sick")) return "sickLeaves";
                        if (name.contains("paid")) return "paidLeaves";
                        return name.replaceAll("\\s+", "") + "Leaves";
                    })
                    .orElse("otherLeaves");
        } catch (Exception e) {
            return "otherLeaves";
        }
    }
}
