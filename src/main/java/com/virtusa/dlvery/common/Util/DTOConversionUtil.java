package com.virtusa.dlvery.common.Util;

import com.virtusa.dlvery.Delivery.DTO.*;
import com.virtusa.dlvery.Delivery.Entities.*;
import com.virtusa.dlvery.Inventory.DTO.*;
import com.virtusa.dlvery.Inventory.Entities.Inventory;
import com.virtusa.dlvery.Inventory.Entities.Order;
import com.virtusa.dlvery.Inventory.Entities.Transaction;
import com.virtusa.dlvery.Inventory.Entities.Warehouse;
import com.virtusa.dlvery.common.DTO.*;
import com.virtusa.dlvery.common.Entities.Category;
import com.virtusa.dlvery.common.Entities.Product;
import com.virtusa.dlvery.common.Entities.UserAssignedRole;
import com.virtusa.dlvery.common.Entities.UserRole;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class DTOConversionUtil {

    public static CategoryDTO convertToCategoryDTO(Category category) {
        return CategoryDTO.builder()
                .categoryId(category.getCategoryId())
                .categoryName(category.getCategoryName())
                .build();
    }

    public static ProductDTO convertToProductDTO(Product product) {
        return ProductDTO.builder()
                .productId(product.getProductId())
                .productName(product.getProductName())
                .category(convertToCategoryDTO(product.getCategory()))
                .isPerishable(product.isPerishable())
                .expiryDate(product.getExpiryDate())
                .isDamaged(product.isDamaged())
               // .warehouse(convertToWarehouseDTO(product.getWarehouse()))
                .build();
    }

    public static List<CategoryDTO> convertToCategoryDTOList(List<Category> categories) {
        return categories.stream()
                .map(DTOConversionUtil::convertToCategoryDTO)
                .collect(Collectors.toList());
    }





    public static Category convertToCategory(CategoryDTO categoryDTO) {
        return Category.builder()
                .categoryId(categoryDTO.getCategoryId())
                .categoryName(categoryDTO.getCategoryName())
                .build();
    }

    public static List<ProductDTO> convertToProductDTOList(List<Product> products) {
        return products.stream()
                .map(DTOConversionUtil::convertToProductDTO)
                .collect(Collectors.toList());
    }



    public static Product convertToProduct(ProductDTO productDTO) {
        return Product.builder()
                .productId(productDTO.getProductId())
                .productName(productDTO.getProductName())
                .category(convertToCategory(productDTO.getCategory()))
                .isPerishable(productDTO.isPerishable())
                .expiryDate(productDTO.getExpiryDate())
                .isDamaged(productDTO.isDamaged())
                .warehouse(convertToWarehouse(productDTO.getWarehouse()))
                .build();
    }

    public static Product convertToProduct(ProductRequestDTO productDTO, Category category) {
        return Product.builder()
                .productId(productDTO.getProductId())
                .productName(productDTO.getProductName())
                .category(category)
                .isPerishable(productDTO.isPerishable())
                .expiryDate(productDTO.getExpiryDate())
                .isDamaged(productDTO.isDamaged())
//                .warehouse(convertToWarehouse(productDTO.getWarehouse()))
                .build();
    }

    public static Set<ProductDTO> convertToProductDTOList(Set<Product> products) {
        if(products == null || products.isEmpty()){
            return null;
        }
        return products.stream()
                .map(DTOConversionUtil::convertToProductDTO)
                .collect(Collectors.toSet());
    }

    public static WarehouseDTO convertToWarehouseDTO(Warehouse warehouse) {
        return WarehouseDTO.builder()
                .warehouseId(warehouse.getWarehouseId())
                .warehouseName(warehouse.getWarehouseName())
                .location(warehouse.getLocation())
                .capacity(warehouse.getCapacity())
                .inventories(warehouse.getInventory())
                .build();
    }

    public static Warehouse convertToWarehouse(WarehouseDTO warehouseDTO) {

        return Warehouse.builder()
                .warehouseId(warehouseDTO.getWarehouseId())
                .warehouseName(warehouseDTO.getWarehouseName())
                .location(warehouseDTO.getLocation())
                .capacity(warehouseDTO.getCapacity())
                .build();
    }


    public static Warehouse convertToWarehouse(WarehouseRequestDTO warehouseDTO) {

        return Warehouse.builder()
                .warehouseId(warehouseDTO.getWarehouseId())
                .warehouseName(warehouseDTO.getWarehouseName())
                .location(warehouseDTO.getLocation())
                .capacity(warehouseDTO.getCapacity())
                .build();
    }

    public static List<WarehouseDTO> convertToWarehouseDTOList(List<Warehouse> warehouses) {
        return warehouses.stream()
                .map(DTOConversionUtil::convertToWarehouseDTO)
                .collect(Collectors.toList());
    }

    public static Set<Product> convertToProductSet(Set<ProductDTO> productDTOSet) {
        if(productDTOSet == null || productDTOSet.isEmpty()){
            return null;
        }
        return productDTOSet.stream()
                .map(DTOConversionUtil::convertToProduct)
                .collect(Collectors.toSet());
    }


    public static InventoryDTO convertToInventoryDTO(Inventory inventory) {
        return InventoryDTO.builder()
                .movementId(inventory.getMovementId())
                .product(inventory.getProduct())
                .quantityIn(inventory.getQuantityIn())
                .quantityOut(inventory.getQuantityOut())
                .movementType(inventory.getMovementType())
                .reason(inventory.getReason())
                .warehouse(inventory.getWarehouse())
                .date(inventory.getDate())
                .build();
    }

    public static List<InventoryDTO> convertToInventoryDTOList(List<Inventory> inventoryList) {
        return inventoryList.stream()
                .map(DTOConversionUtil::convertToInventoryDTO)
                .collect(Collectors.toList());
    }

    public static Inventory convertToInventory(InventoryDTO inventoryDTO) {

        return Inventory.builder()
                .movementId(inventoryDTO.getMovementId())

                .quantityIn(inventoryDTO.getQuantityIn())
                .quantityOut(inventoryDTO.getQuantityOut())
                .movementType(inventoryDTO.getMovementType())
                .reason(inventoryDTO.getReason())
                .date(inventoryDTO.getDate())
                .build();
    }

    public static Inventory convertToInventory(InventoryRequestDTO inventoryDTO, Product product, Warehouse warehouse) {

        return Inventory.builder()
                .movementId(inventoryDTO.getMovementId())
                .product(product)
                .quantityIn(inventoryDTO.getQuantityIn())
                .quantityOut(inventoryDTO.getQuantityOut())
                .movementType(inventoryDTO.getMovementType())
                .reason(inventoryDTO.getReason())
                .date(inventoryDTO.getDate())
                .warehouse(warehouse)
                .build();
    }

    public static TransactionDTO convertToTransactionDTO(Transaction transaction) {
        return new TransactionDTO(transaction);
    }

    public static List<TransactionDTO> convertToTransactionDTOList(List<Transaction> transactions) {
        return transactions.stream()
                .map(DTOConversionUtil::convertToTransactionDTO)
                .collect(Collectors.toList());
    }

    public static Transaction convertToTransaction(TransactionDTO transactionDTO) {
        //Order order = orderService.findById(transactionDTO.getOrderId()); // Assuming there is a service to fetch the Order entity

        return Transaction.builder()
                .transactionId(transactionDTO.getTransactionId())
                //  .order(order)
                .amount(transactionDTO.getAmount())
                .transactionDate(transactionDTO.getTransactionDate())
                .paymentStatus(transactionDTO.getPaymentStatus())
                .build();
    }

    public static OrderDTO convertToOrderDTO(Order order) {
        return new OrderDTO(order);
    }

    public static List<OrderDTO> convertToOrderDTOList(List<Order> orders) {
        return orders.stream()
                .map(DTOConversionUtil::convertToOrderDTO)
                .collect(Collectors.toList());
    }

    public static Order convertToOrder(OrderDTO orderDTO) {
        // Assuming there is a service to fetch the User entity by userId
        // You need to implement this part based on your actual entity relationships
        //  User user = userService.findById(orderDTO.getUserId());

        return Order.builder()
                .orderId(orderDTO.getOrderId())
                //.user(user)
                .deliveryAddress(orderDTO.getDeliveryAddress())
                .orderDate(orderDTO.getOrderDate())
                .isCompleted(orderDTO.isCompleted())
                .build();
    }

    public static DeliveryAgentDTO convertToDeliveryAgentDTO(DeliveryAgent deliveryAgent) {
        return DeliveryAgentDTO.builder()
                .agentId(deliveryAgent.getAgentId())
                .agentName(deliveryAgent.getAgentName())
                .build();
    }

    public static List<DeliveryAgentDTO> convertToDeliveryAgentDTOList(List<DeliveryAgent> deliveryAgents) {
        return deliveryAgents.stream()
                .map(DTOConversionUtil::convertToDeliveryAgentDTO)
                .collect(Collectors.toList());
    }

    public static DeliveryAgent convertToDeliveryAgent(DeliveryAgentDTO deliveryAgentDTO) {
        return DeliveryAgent.builder()
                .agentId(deliveryAgentDTO.getAgentId())
                .agentName(deliveryAgentDTO.getAgentName())
                .build();
    }

    public static DeliveryDTO convertToDeliveryDTO(Delivery delivery) {
        return DeliveryDTO.builder()
                .deliveryId(delivery.getDeliveryId())
                .product(convertToProductDTO(delivery.getProduct()))
                .deliveryAgent(convertToDeliveryAgentDTO(delivery.getDeliveryAgent()))
                .status(delivery.getStatus())
                .date(delivery.getDate())
                .isDamaged(delivery.isDamaged())
                .warehouse(convertToWarehouseDTO(delivery.getWarehouse()))
                .build();
    }

    public static List<DeliveryDTO> convertToDeliveryDTOList(List<Delivery> deliveries) {
        return deliveries.stream()
                .map(DTOConversionUtil::convertToDeliveryDTO)
                .collect(Collectors.toList());
    }

    public static Delivery convertToDelivery(DeliveryDTO deliveryDTO) {
        return Delivery.builder()
                .deliveryId(deliveryDTO.getDeliveryId())
                .product(convertToProduct(deliveryDTO.getProduct()))
                .deliveryAgent(convertToDeliveryAgent(deliveryDTO.getDeliveryAgent()))
                .status(deliveryDTO.getStatus())
                .date(deliveryDTO.getDate())
                .isDamaged(deliveryDTO.isDamaged())
                .warehouse(convertToWarehouse(deliveryDTO.getWarehouse()))
                .build();
    }

    public static DeliveryAgentDeviceDTO convertToDeliveryAgentDeviceDTO(DeliveryAgentDevice device) {
        return DeliveryAgentDeviceDTO.builder()
                .deviceId(device.getDeviceId())
                .agent(convertToDeliveryAgentDTO(device.getAgent()))
                .deviceName(device.getDeviceName())
                .deviceType(device.getDeviceType())
                .build();
    }

    public static List<DeliveryAgentDeviceDTO> convertToDeliveryAgentDeviceDTOList(List<DeliveryAgentDevice> devices) {
        return devices.stream()
                .map(DTOConversionUtil::convertToDeliveryAgentDeviceDTO)
                .collect(Collectors.toList());
    }

    public static DeliveryAgentDevice convertToDeliveryAgentDevice(DeliveryAgentDeviceDTO deviceDTO) {
        return DeliveryAgentDevice.builder()
                .deviceId(deviceDTO.getDeviceId())
                .agent(convertToDeliveryAgent(deviceDTO.getAgent()))
                .deviceName(deviceDTO.getDeviceName())
                .deviceType(deviceDTO.getDeviceType())
                .build();
    }

    public static List<DeliveryOrderAssociationDTO> convertToDeliveryOrderAssociationDTOList(List<DeliveryOrderAssociation> associations) {
        return associations.stream()
                .map(DTOConversionUtil::convertToDeliveryOrderAssociationDTO)
                .collect(Collectors.toList());
    }

    public static DeliveryOrderAssociationDTO convertToDeliveryOrderAssociationDTO(DeliveryOrderAssociation association) {
        return DeliveryOrderAssociationDTO.builder()
                .deliveryId(association.getDelivery().getDeliveryId())
                .orderId(association.getOrder().getOrderId())
                .build();
    }

    public static DeliveryOrderAssociation convertToDeliveryOrderAssociation(DeliveryOrderAssociationDTO deliveryOrderAssociationDTO) {
        if (deliveryOrderAssociationDTO == null) {
            return null;
        }

        return DeliveryOrderAssociation.builder()
                //   .delivery(deliveryOrderAssociationDTO.getDeliveryId())
                //   .order(deliveryOrderAssociationDTO.getOrderId())
                .build();
    }

    public static List<DeliveryTrackingDTO> convertToDeliveryTrackingDTOList(List<DeliveryTracking> trackingList) {
        return trackingList.stream()
                .map(DTOConversionUtil::convertToDeliveryTrackingDTO)
                .collect(Collectors.toList());
    }

    public static DeliveryTrackingDTO convertToDeliveryTrackingDTO(DeliveryTracking tracking) {
        return DeliveryTrackingDTO.builder()
                .trackingId(tracking.getTrackingId())
                .deliveryId(tracking.getDelivery().getDeliveryId())
                .customerName(tracking.getCustomerName())
                .customerSignature(tracking.getCustomerSignature())
                .doorLockStatus(tracking.isDoorLockStatus())
                .isMissedDelivery(tracking.isMissedDelivery())
                .isDamagedDelivery(tracking.isDamagedDelivery())
                .isReturnDelivery(tracking.isReturnDelivery())
                .createdAt(tracking.getCreatedAt())
                .build();
    }

    public static DeliveryTracking convertToDeliveryTracking(DeliveryTrackingDTO trackingDTO) {
        return DeliveryTracking.builder()
                .trackingId(trackingDTO.getTrackingId())
                //.deliveryId(trackingDTO.getDeliveryId())
                .customerName(trackingDTO.getCustomerName())
                .customerSignature(trackingDTO.getCustomerSignature())
                .doorLockStatus(trackingDTO.isDoorLockStatus())
                .isMissedDelivery(trackingDTO.isMissedDelivery())
                .isDamagedDelivery(trackingDTO.isDamagedDelivery())
                .isReturnDelivery(trackingDTO.isReturnDelivery())
                .createdAt(trackingDTO.getCreatedAt())
                .build();
    }

    public static List<DeliveryTracking> convertToDeliveryTrackingList(List<DeliveryTrackingDTO> trackingDTOList) {
        return trackingDTOList.stream()
                .map(DTOConversionUtil::convertToDeliveryTracking)
                .collect(Collectors.toList());
    }





    public static DeliveryScheduleDTO convertToDeliveryScheduleDTO(DeliverySchedule deliverySchedule) {
        return DeliveryScheduleDTO.builder()
                .scheduleId(deliverySchedule.getScheduleId())
                .agentId(deliverySchedule.getAgent().getAgentId())
                .deliveryId(deliverySchedule.getDelivery().getDeliveryId())
                .deliveryDate(deliverySchedule.getDeliveryDate())
                .isCompleted(deliverySchedule.isCompleted())
                .assignedDate(deliverySchedule.getAssignedDate())
                .deliveryData(deliverySchedule.getDeliveryData())
                .priorityId(deliverySchedule.getPriority().getPriorityId())
                //    .scheduleId(deliverySchedule.isRescheduled())
                .build();
    }

    public static List<DeliveryScheduleDTO> convertToDeliveryScheduleDTOList(List<DeliverySchedule> deliverySchedules) {
        return deliverySchedules.stream()
                .map(DTOConversionUtil::convertToDeliveryScheduleDTO)
                .collect(Collectors.toList());
    }

    public static DeliverySchedule convertToDeliverySchedule(DeliveryScheduleDTO deliveryScheduleDTO) {
        return DeliverySchedule.builder()
                .scheduleId(deliveryScheduleDTO.getScheduleId())
                // Assuming you have a service method to retrieve DeliveryAgent by agentId
                //  .agent(deliveryAgentService.findById(deliveryScheduleDTO.getAgentId()))
                // Assuming you have a service method to retrieve Delivery by deliveryId
                //   .delivery(deliveryService.findById(deliveryScheduleDTO.getDeliveryId()))
                .deliveryDate(deliveryScheduleDTO.getDeliveryDate())
                .isCompleted(deliveryScheduleDTO.isCompleted())
                .assignedDate(deliveryScheduleDTO.getAssignedDate())
                .deliveryData(deliveryScheduleDTO.getDeliveryData())
                // Assuming you have a service method to retrieve DeliveryPriority by priorityId
                //  .priority(deliveryPriorityService.findById(deliveryScheduleDTO.getPriorityId()))
                .isRescheduled(deliveryScheduleDTO.isRescheduled())
                .build();
    }

    // ... (previous methods)

    public static DeliveryHistoryDTO convertToDeliveryHistoryDTO(DeliveryHistory deliveryHistory) {
        return DeliveryHistoryDTO.builder()
                .historyId(deliveryHistory.getHistoryId())
                .scheduleId(deliveryHistory.getSchedule().getScheduleId())
                .eventType(deliveryHistory.getEventType())
                .eventDescription(deliveryHistory.getEventDescription())
                .eventTimestamp(deliveryHistory.getEventTimestamp())
                .deliveryAgentName(deliveryHistory.getDeliveryAgentName())
                .customerName(deliveryHistory.getCustomerName())
                .build();
    }

    public static List<DeliveryHistoryDTO> convertToDeliveryHistoryDTOList(List<DeliveryHistory> deliveryHistoryList) {
        return deliveryHistoryList.stream()
                .map(DTOConversionUtil::convertToDeliveryHistoryDTO)
                .collect(Collectors.toList());
    }

    public static DeliveryHistory convertToDeliveryHistory(DeliveryHistoryDTO deliveryHistoryDTO) {
        return DeliveryHistory.builder()
                .historyId(deliveryHistoryDTO.getHistoryId())
                // Assuming you have a service method to retrieve DeliverySchedule by scheduleId
                //     .schedule(deliveryScheduleService.findById(deliveryHistoryDTO.getScheduleId()))
                .eventType(deliveryHistoryDTO.getEventType())
                .eventDescription(deliveryHistoryDTO.getEventDescription())
                .eventTimestamp(deliveryHistoryDTO.getEventTimestamp())
                .deliveryAgentName(deliveryHistoryDTO.getDeliveryAgentName())
                .customerName(deliveryHistoryDTO.getCustomerName())
                .build();
    }




    public static DeliveryPriorityDTO convertToDeliveryPriorityDTO(DeliveryPriority deliveryPriority) {
        return DeliveryPriorityDTO.builder()
                .priorityId(deliveryPriority.getPriorityId())
                .priorityName(deliveryPriority.getPriorityName())
                .build();
    }

    public static List<DeliveryPriorityDTO> convertToDeliveryPriorityDTOList(List<DeliveryPriority> deliveryPriorities) {
        return deliveryPriorities.stream()
                .map(DTOConversionUtil::convertToDeliveryPriorityDTO)
                .collect(Collectors.toList());
    }

    public static DeliveryPriority convertToDeliveryPriority(DeliveryPriorityDTO deliveryPriorityDTO) {
        return DeliveryPriority.builder()
                .priorityId(deliveryPriorityDTO.getPriorityId())
                .priorityName(deliveryPriorityDTO.getPriorityName())
                .build();
    }


    public static UserAssignedRoleDTO convertToUserAssignedRoleDTO(UserAssignedRole userAssignedRole) {
        return UserAssignedRoleDTO.builder()
                .assignmentId(userAssignedRole.getAssignmentId())
                .userId(userAssignedRole.getUser().getUserId())
                //.roleId(userAssignedRole.getRole().getRoleId())
                .build();
    }

    public static List<UserAssignedRoleDTO> convertToUserAssignedRoleDTOList(List<UserAssignedRole> userAssignedRoles) {
        return userAssignedRoles.stream()
                .map(DTOConversionUtil::convertToUserAssignedRoleDTO)
                .collect(Collectors.toList());
    }

    public static UserAssignedRole convertToUserAssignedRole(UserAssignedRoleDTO userAssignedRoleDTO) {
        // Assuming you have service methods to retrieve User and UserRole by userId and roleId
//            User user = userService.findById(userAssignedRoleDTO.getUserId());
//            UserRole role = userRoleService.findById(userAssignedRoleDTO.getRoleId());

        return UserAssignedRole.builder()
                .assignmentId(userAssignedRoleDTO.getAssignmentId())
//                    .user(user)
//                    .role(role)
                .build();
    }

    public static List<UserAssignedRole> convertToUserAssignedRoleList(List<UserAssignedRoleDTO> userAssignedRoleDTOList) {
        return userAssignedRoleDTOList.stream()
                .map(DTOConversionUtil::convertToUserAssignedRole)
                .collect(Collectors.toList());
    }

        public static UserRoleDTO convertToUserRoleDTO(UserRole userRole) {
            return UserRoleDTO.builder()
                    .roleId(userRole.getId())
                    .roleName(userRole.getRoleName())
                    .build();
        }

        public static List<UserRoleDTO> convertToUserRoleDTOList(List<UserRole> userRoles) {
            return userRoles.stream()
                    .map(DTOConversionUtil::convertToUserRoleDTO)
                    .collect(Collectors.toList());
        }

        public static UserRole convertToUserRole(UserRoleDTO userRoleDTO) {
            // Assuming you have a service method to retrieve User by userId
         //   User user = userService.findById(userRoleDTO.getUserIds().isEmpty() ? null : userRoleDTO.getUserIds().iterator().next());

            return UserRole.builder()
               //     .id(userRoleDTO.getRoleId())
             //       .user(user)
                    .roleName(userRoleDTO.getRoleName())
                    .build();
        }

        public static List<UserRole> convertToUserRoleList(List<UserRoleDTO> userRoleDTOList) {
            return userRoleDTOList.stream()
                    .map(DTOConversionUtil::convertToUserRole)
                    .collect(Collectors.toList());
        }

        // ... (remaining methods)
    }




