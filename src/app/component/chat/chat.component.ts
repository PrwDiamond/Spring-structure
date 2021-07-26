import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import * as SockJS from 'sockjs-client';
import { ChatMessage } from 'src/app/interfaces/chat-message';
import { ChatService } from 'src/app/services/chat.service';
import * as Stomp from 'stompjs';

@Component({
  selector: 'app-chat',
  templateUrl: './chat.component.html',
  styleUrls: ['./chat.component.scss'],
})
export class ChatComponent implements OnInit {
  private stompClient: any;

  private CHANNEL = '/topic/chat';

  private ENDPOINT = 'http://localhost:8080/socket';

  messages: ChatMessage[] = [];

  isConnected = false;

  chatFromGroup: FormGroup = new FormGroup({
    message: new FormControl('', Validators.required),
  });

  constructor(private chatService: ChatService) {}

  ngOnInit(): void {
    this.connectWebSocket();
  }

  private connectWebSocket() {
    let ws = new SockJS(this.ENDPOINT);
    this.stompClient = Stomp.over(ws);

    this.stompClient.connect({},  () => {
      this.isConnected = true;
      this.subscribeToGlobalChat();
    });
  } 

  private subscribeToGlobalChat() {
    this.stompClient.subscribe(this.CHANNEL, (message: any) => {

      let newMessage = JSON.parse(message.body) as ChatMessage;
      console.log(newMessage);

      this.messages.push(newMessage);
    });
  }

  onSubmit() {
    let message = this.chatFromGroup.controls.message.value;

    //is connect?
    if (!this.isConnected) {
      alert('Please connect to WebSocket');
      return;
    }

    //validate
    alert('Ready to send');
    this.chatService.postMessage(message).subscribe(
      (response) => {
        console.log(response);
      },
      (error) => {
        console.log(error);
      }
    );
  }
}
