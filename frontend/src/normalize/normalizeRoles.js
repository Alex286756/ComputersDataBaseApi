import { normalize, schema } from "normalizr"

export function normalizeRoles(rolesList) {
    const roleSchema = new schema.Entity('roles')

    return normalize(rolesList, [roleSchema])
}
