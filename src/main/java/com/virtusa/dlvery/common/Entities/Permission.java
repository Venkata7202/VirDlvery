package com.virtusa.dlvery.common.Entities;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Permission {

    INVENTORY_READ("inventory:read"),
    INVENTORY_UPDATE("inventory:update"),
    INVENTORY_CREATE("inventory:create"),
    INVENTORY_DELETE("inventory:delete"),
    DELIVERY_READ("delivery:read"),
    DELIVERY_UPDATE("delivery:update"),
    DELIVERY_CREATE("delivery:create"),
    DELIVERY_DELETE("delivery:delete");



    @Getter final String permission;
}
