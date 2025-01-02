import { createSlice } from "@reduxjs/toolkit";
import { normalizeDevices } from "../normalize/normalizeDevices";

const initialState = {
    index: [],
    devices: []
}

export const deviceSlice = createSlice({
    name: 'devices',
    initialState,
    reducers: {
        setDevices: (state, action) => {
            const { result, entities } = normalizeDevices(action.payload)
            state.index = result
            state.devices = entities.devices ? entities.devices : []   
        },
        addDevice: (state, action) => {
            state.index.push(action.payload.index)
            state.devices[action.payload.index] = action.payload.device
        },
        editDevice: (state, action) => {
            state.devices[action.payload.index] = action.payload.device
        },
        deleteDevice: (state, action) => {
            const indexDelete = state.index.indexOf(action.payload.index[0])
            state.index.splice(indexDelete, 1)
            delete state.devices[action.payload.index]
        },        
    },
})

export const { setDevices, addDevice, editDevice, deleteDevice } = deviceSlice.actions

export default deviceSlice.reducer