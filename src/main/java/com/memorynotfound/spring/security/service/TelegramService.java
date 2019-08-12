package com.memorynotfound.spring.security.service;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.request.ParseMode;
import com.pengrad.telegrambot.request.SendMessage;


public class TelegramService {

	 public static void Bot(String mensaje) {
		 String BOT_TKEN = "706379608";
		 String chatId = "873670709:AAFmbxsy7eADeB3isWBJP_eZZWlH4ouSHXU";
		 TelegramBot bot = new TelegramBot(BOT_TKEN);
		 SendMessage request = new SendMessage(chatId, mensaje)
			        .parseMode(ParseMode.HTML)
			        .disableWebPagePreview(true)
			        .disableNotification(true);
			   
		bot.execute(request);
	 }
}
