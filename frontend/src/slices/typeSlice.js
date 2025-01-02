import { createSlice } from "@reduxjs/toolkit";
import { normalizeTypes } from "../normalize/normalizeTypes";

const initialState = {
    index: [],
    types: []
}

export const typeSlice = createSlice({
    name: 'types',
    initialState,
    reducers: {
        setTypes: (state, action) => {
            const { result, entities } = normalizeTypes(action.payload)
            state.index = result
            state.types = entities.types ? entities.types : []   
        },
        addType: (state, action) => {
            state.index.push(action.payload.index)
            state.types[action.payload.index] = action.payload.type   
        },
    },
})

export const { setTypes, addType } = typeSlice.actions

export default typeSlice.reducer