import { normalize, schema } from "normalizr"

export function normalizeModels(modelsList) {
    const modelSchema = new schema.Entity('models')

    return normalize(modelsList, [modelSchema])
}
