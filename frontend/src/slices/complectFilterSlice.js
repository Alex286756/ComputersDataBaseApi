import { createSlice } from "@reduxjs/toolkit";

const initialState = {
    complectsFilter: [],
}

export const complectsFilterSlice = createSlice({
    name: 'complectsFilter',
    initialState,
    reducers: {
        setComplectFilter: (state, action) => {
            state.complectsFilter = action.payload
        },
    },
})

export const { setComplectFilter } = complectsFilterSlice.actions

export default complectsFilterSlice.reducer