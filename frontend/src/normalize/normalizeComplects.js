import { normalize, schema } from "normalizr"

export function normalizeComplects(complectsList) {
    const complectSchema = new schema.Entity('complects')

    return normalize(complectsList, [complectSchema])
}
