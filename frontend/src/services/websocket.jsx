import { Client } from "@stomp/stompjs";
import SockJS from "sockjs-client";

const client = new Client({
    webSocketFactory: () => new SockJS("http://localhost:8080/ws"),
    onConnect: () => {
        console.log("WebSocket connected");
        client.subscribe("/topic/orders/1", (message) => {
            console.log("Order Update:", JSON.parse(message.body));
        });

        client.subscribe("/topic/admin/1", (message) => {
            console.log("Admin Update:", JSON.parse(message.body));
        });
        client.subscribe("/topic/admin/stock/1", (message) => {
            console.log("Admin Stock Update:", JSON.parse(message.body));
        });
    },
    onDisconnect: () => {
        console.log("WebSocket disconnected");
    }
})
export const connectWebSocket = () => client.activate();
export const disconnectWebSocket = () => client.deactivate();   