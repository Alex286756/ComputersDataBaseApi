import { createSlice } from "@reduxjs/toolkit";

const initialState = {
    userState: "start",
}

export const userStateSlice = createSlice({
    name: 'user',
    initialState,
    reducers: {
        setUserState: (state, action) => {
            state.userState = action.payload
        },
    },
})

export const { setUserState } = userStateSlice.actions

export default userStateSlice.reducer