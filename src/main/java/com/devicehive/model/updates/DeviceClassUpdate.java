package com.devicehive.model.updates;


import com.devicehive.json.strategies.JsonPolicyDef;
import com.devicehive.model.DeviceClass;
import com.devicehive.model.Equipment;
import com.devicehive.model.HiveEntity;
import com.devicehive.model.JsonStringWrapper;

import java.util.Optional;
import java.util.Set;

import static com.devicehive.json.strategies.JsonPolicyDef.Policy.*;

public class DeviceClassUpdate implements HiveEntity {

    private static final long serialVersionUID = 967472386318199376L;
    @JsonPolicyDef({DEVICE_PUBLISHED, DEVICE_SUBMITTED, NETWORK_PUBLISHED, DEVICECLASS_LISTED, DEVICECLASS_PUBLISHED})
    private Optional<String> id;
    @JsonPolicyDef({DEVICE_PUBLISHED, DEVICE_SUBMITTED, NETWORK_PUBLISHED, DEVICECLASS_LISTED, DEVICECLASS_PUBLISHED})
    private Optional<Boolean> isPermanent;
    @JsonPolicyDef({DEVICE_PUBLISHED, DEVICE_SUBMITTED, NETWORK_PUBLISHED, DEVICECLASS_LISTED, DEVICECLASS_PUBLISHED})
    private Optional<Integer> offlineTimeout;
    @JsonPolicyDef({DEVICE_PUBLISHED, DEVICE_SUBMITTED, NETWORK_PUBLISHED, DEVICECLASS_LISTED, DEVICECLASS_PUBLISHED})
    private Optional<JsonStringWrapper> data;
    @JsonPolicyDef({DEVICECLASS_PUBLISHED, DEVICE_SUBMITTED})
    private Optional<Set<Equipment>> equipment;

    public Optional<Set<Equipment>> getEquipment() {
        return equipment;
    }

    public void setEquipment(Optional<Set<Equipment>> equipment) {
        this.equipment = equipment;
    }

    public Optional<String> getId() {
        return id;
    }

    public void setId(Optional<String> id) {
        this.id = id;
    }

    public Optional<Boolean> getPermanent() {
        return isPermanent;
    }

    public void setPermanent(Optional<Boolean> permanent) {
        isPermanent = permanent;
    }

    public Optional<Integer> getOfflineTimeout() {
        return offlineTimeout;
    }

    public void setOfflineTimeout(Optional<Integer> offlineTimeout) {
        this.offlineTimeout = offlineTimeout;
    }

    public Optional<JsonStringWrapper> getData() {
        return data;
    }

    public void setData(Optional<JsonStringWrapper> data) {
        this.data = data;
    }

    public DeviceClass convertTo() {
        DeviceClass deviceClass = new DeviceClass();
        if (isPermanent != null) {
            deviceClass.setPermanent(isPermanent.orElse(null));
        }
        if (offlineTimeout != null) {
            deviceClass.setOfflineTimeout(offlineTimeout.orElse(null));
        }
        if (data != null) {
            deviceClass.setData(data.orElse(null));
        }
        if (id != null) {
            deviceClass.setId(id.orElse(null));
        }
        if (equipment != null) {
            deviceClass.setEquipment(equipment.orElse(null));
        }
        return deviceClass;
    }
}
