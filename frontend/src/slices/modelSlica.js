import { createSlice } from "@reduxjs/toolkit";
import { normalizeModels } from "../normalize/normalizeModels";

const initialState = {
    index: [],
    models: [],
}

export const modelSlice = createSlice({
    name: 'models',
    initialState,
    reducers: {
        setModels: (state, action) => {
            const { result, entities } = normalizeModels(action.payload)
            state.index = result
            state.models = entities.models ? entities.models : []   
        },
        addModel: (state, action) => {
            state.index.push(action.payload.index)
            state.models[action.payload.index] = action.payload.model
        },
    },
})

export const { setModels, addModel } = modelSlice.actions

export default modelSlice.reducer