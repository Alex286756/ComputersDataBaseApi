import { createSlice } from "@reduxjs/toolkit";
import { normalizeRoles } from "../normalize/normalizeRoles";

const initialState = {
    index: [],
    roles: [],
}

export const roleSlice = createSlice({
    name: 'roles',
    initialState,
    reducers: {
        setRoles: (state, action) => {
            const { result, entities } = normalizeRoles(action.payload)
            state.index = result
            state.roles = entities.roles    
        },
    },
})

export const { setRoles } = roleSlice.actions

export default roleSlice.reducer