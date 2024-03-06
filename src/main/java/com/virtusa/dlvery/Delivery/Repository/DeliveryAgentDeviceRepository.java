package com.virtusa.dlvery.Delivery.Repository;

import com.virtusa.dlvery.Delivery.Entities.DeliveryAgent;
import com.virtusa.dlvery.Delivery.Entities.DeliveryAgentDevice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;




@Repository
public interface DeliveryAgentDeviceRepository extends JpaRepository<DeliveryAgentDevice, UUID> {

    List<DeliveryAgentDevice> findByAgent_AgentId(UUID agentId);

    List<DeliveryAgentDevice> findByDeviceType(String deviceType);


    List<DeliveryAgentDevice> findByDeviceTypeAndAgent_AgentIdIn(String deviceType, List<UUID> agentIds);

    DeliveryAgentDevice findDeliveryAgentDeviceByDeviceId(UUID deviceId);


    List<DeliveryAgentDevice> findDeliveryAgentDevicesByAgent(DeliveryAgent agent);


}
