import { Client } from "@stomp/stompjs";
import SockJS from "sockjs-client";

const client = new Client({
    webSocketFactory: () => new SockJS("http://localhost:8080/ws"),
    connectHeaders:{
        Authorization: `Bearer ${localStorage.getItem("token")}`,
    },
    onConnect: () => {
        console.log("WebSocket connected");
        client.subscribe("/topic/orders/1", (message) => {
            console.log("Order Update:", JSON.parse(message.body));
        });
    },
    onDisconnect: () => {
        console.log("WebSocket disconnected");
    }
});
export const connectWebSocket = () => client.activate();
export const disconnectWebSocket = () => client.deactivate();   