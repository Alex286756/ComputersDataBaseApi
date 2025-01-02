import { normalize, schema } from "normalizr"

export function normalizeUsers(usersList) {
    const userSchema = new schema.Entity('users')

    return normalize(usersList, [userSchema])
}
