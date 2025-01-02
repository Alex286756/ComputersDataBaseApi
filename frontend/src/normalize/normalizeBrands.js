import { normalize, schema } from "normalizr"

export function normalizeBrands(brandsList) {
    const brandSchema = new schema.Entity('brands')

    return normalize(brandsList, [brandSchema])
}
