import { configureStore } from "@reduxjs/toolkit";
import deviceReducer from './slices/deviceSlice';
import userStateReducer from "./slices/userStateSlice";
import devicesFilterReducer from "./slices/devicesFilterSlice";
import typeReducer from "./slices/typeSlice";
import complectReducer from "./slices/complectSlice";
import complectsFilterReducer from "./slices/complectFilterSlice";
import brandsReducer from "./slices/brandSlice";
import modelReducer from "./slices/modelSlica";
import roleReducer from "./slices/roleSlice";
import userReducer from "./slices/userSlice";
import urlReducer from "./slices/urlSlice";

export const store = configureStore({
    reducer: {
        brands: brandsReducer,
        complectsFilter: complectsFilterReducer,
        complects: complectReducer,
        devicesFilter: devicesFilterReducer,
        devices: deviceReducer,
        models: modelReducer,
        roles: roleReducer,
        types: typeReducer,
        users: userReducer,
        userState: userStateReducer,
        apiURL: urlReducer,
    },
})