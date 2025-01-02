import { normalize, schema } from "normalizr"

export function normalizeTypes(typesList) {
    const typeSchema = new schema.Entity('types')

    return normalize(typesList, [typeSchema])
}
