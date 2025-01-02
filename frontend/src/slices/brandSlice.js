import { createSlice } from "@reduxjs/toolkit";
import { normalizeBrands } from "../normalize/normalizeBrands";

const initialState = {
    index: [],
    brands: [],
}

export const brandSlice = createSlice({
    name: 'brands',
    initialState,
    reducers: {
        setBrands: (state, action) => {
            const { result, entities } = normalizeBrands(action.payload)
            state.index = result
            state.brands = entities.brands ? entities.brands : [] 
        },
        addBrand: (state, action) => {
            state.index.push(action.payload.index)
            state.brands[action.payload.index] = action.payload.brand  
        },
    },
})

export const { setBrands, addBrand } = brandSlice.actions

export default brandSlice.reducer