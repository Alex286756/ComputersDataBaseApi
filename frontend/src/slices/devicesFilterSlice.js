import { createSlice } from "@reduxjs/toolkit";

const initialState = {
    devicesFilter: [],
}

export const devicesFilterSlice = createSlice({
    name: 'devicesFilter',
    initialState,
    reducers: {
        setDevicesFilter: (state, action) => {
            state.devicesFilter = action.payload
        },
    },
})

export const { setDevicesFilter } = devicesFilterSlice.actions

export default devicesFilterSlice.reducer