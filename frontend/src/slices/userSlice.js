import { createSlice } from "@reduxjs/toolkit";
import { normalizeUsers } from "../normalize/normalizeUsers";

const initialState = {
    index: [],
    users: [],
}

export const userSlice = createSlice({
    name: 'users',
    initialState,
    reducers: {
        setUsers: (state, action) => {
            const { result, entities } = normalizeUsers(action.payload)
            state.index = result
            state.users = entities.users    
        },
    },
})

export const { setUsers } = userSlice.actions

export default userSlice.reducer