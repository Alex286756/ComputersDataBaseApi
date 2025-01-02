import { createSlice } from "@reduxjs/toolkit";

const initialState = {
    apiURL: 'http://176.108.254.234:8081/compapi/v1/',
    apiJwt: ""
}

export const urlSlice = createSlice({
    name: 'url',
    initialState,
    reducers: {
        setJwt: (state, action) => {
            state.apiJwt = action.payload
        },
        deleteJwt: (state) => {
            state.apiJwt = ""
        },
    },
})

export const { setJwt, deleteJwt } = urlSlice.actions

export default urlSlice.reducer