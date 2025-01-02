import { createSlice } from "@reduxjs/toolkit";
import { normalizeComplects } from "../normalize/normalizeComplects";

const initialState = {
    index: [],
    complects: [],
}

export const complectSlice = createSlice({
    name: 'complects',
    initialState,
    reducers: {
        setComplects: (state, action) => {
            const { result, entities } = normalizeComplects(action.payload)
            state.index = result
            state.complects = entities.complects    
        },
        addComplect: (state, action) => {
            state.index.push(action.payload.index)
            state.complects[action.payload.index] = action.payload.complect
        },
        deleteDeviceFromComplect: (state, action) => {
            const devices = state.complects[action.payload.index].devicesId
            state.complects[action.payload.index].devicesId = devices
                .filter((item) => item.id != action.payload.deviceId)
        },        
    },
})

export const { setComplects, addComplect, deleteDeviceFromComplect } = complectSlice.actions

export default complectSlice.reducer