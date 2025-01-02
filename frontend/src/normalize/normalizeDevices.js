import { normalize, schema } from "normalizr"

export function normalizeDevices(devicesList) {
    const deviceSchema = new schema.Entity('devices')

    return normalize(devicesList, [deviceSchema])
}
