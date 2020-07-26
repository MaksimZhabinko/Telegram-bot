package com.myTelegramBot;

import com.myTelegramBot.controller.BotController;
import com.myTelegramBot.dto.CityDto;
import com.myTelegramBot.dto.PlaceDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Component
public class ExampleBot extends TelegramLongPollingBot {

	private static final Logger logger = LoggerFactory.getLogger(ExampleBot.class);
	private ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
	private long chatId;
	private String pastText = new String();

	private BotController controller;

	@Autowired
	public ExampleBot(BotController controller) {
		this.controller = controller;
	}

	@Value("${bot.token}")
	private String token;

	@Value("${bot.username}")
	private String username;

	@Override
	public String getBotToken() {
		return token;
	}

	@Override
	public String getBotUsername() {
		return username;
	}

	@Override
	public void onUpdateReceived(Update update) {
		String text = update.getMessage().getText();
		chatId = update.getMessage().getChatId();

		SendMessage sendMessage = new SendMessage().setChatId(update.getMessage().getChatId());
		sendMessage.setReplyMarkup(replyKeyboardMarkup);
		try {
			sendMessage.setText(getMessage(text));
			execute(sendMessage);
			replyKeyboardMarkup.setKeyboard(new ArrayList<>());
		} catch (TelegramApiException e) {
			e.printStackTrace();
		}
	}

	@PostConstruct
	public void start() {
		logger.info("username: {}, token: {}", username, token);
	}

	public String getMessage(String msg) {
		msg.trim();
		ArrayList<KeyboardRow> keyboard = new ArrayList<>();
		KeyboardRow keyboardRowFirst = new KeyboardRow();
		KeyboardRow keyboardRowSecond = new KeyboardRow();

		replyKeyboardMarkup.setSelective(true);
		replyKeyboardMarkup.setResizeKeyboard(true);
		replyKeyboardMarkup.setOneTimeKeyboard(true);

		/*
		 * This method is used to launch the bot.
		 */
		if (msg.equals("/start")) {
			return "Which city do you want to visit?";
		}


		/*
		 * This method is used to launch the bot interaction menu.
		 */
		if (msg.equals("/menu")) {
			keyboardRowFirst.add("/deleteCity");
			keyboardRowFirst.add("/addCity");
			keyboardRowSecond.add("/addPlace");
			keyboard.add(keyboardRowFirst);
			keyboard.add(keyboardRowSecond);
			replyKeyboardMarkup.setKeyboard(keyboard);
			return "Choose...";
		}

		List<CityDto> cityDtoList = controller.getAllCity();

		/*
		 * This method is used to get the place of visit in the selected city from the database.
		 */
		for (CityDto cityDto : cityDtoList) {
			if (msg.equals(cityDto.getCity())) {
				PlaceDto placeDto = controller.findPlaceFromCity(msg);
				String place = placeDto.getPlace();
				if (place != null) {
					return place;
				} else {
					return "There is nothing in the database about this city";
				}
			}
		}

		/*
		 * This method is used to launch the interaction menu to remove cities from the database.
		 */
		if (msg.equals("/deleteCity")) {
			for (CityDto cityDto : cityDtoList) {
				keyboardRowFirst.add("Delete - " + cityDto.getCity());
			}
			keyboard.add(keyboardRowFirst);
			replyKeyboardMarkup.setKeyboard(keyboard);
			return "Choose...";
		}

		if (msg.equals("/addCity")) {
			pastText = "/addCity";
			return "Write the city. Example: \"/Moscow\".";
		}

		if (msg.equals("/addPlace")) {
			pastText = "/addPlace";
			return "Write place to city. Example: \"/Moscow - Red Square\".";
		}

		/*
		 * This method is used to remove a city from the database.
		 */
		for (CityDto cityDto : cityDtoList) {
			if (msg.equals("Delete - " + cityDto.getCity())) {
				controller.DeleteCity(cityDto.getCity());
				return cityDto.getCity() + " - Deleted";
			}
		}

		/*
		 * This method is used to save place to the database.
		 */
		if (msg.contains("/") && msg.contains("-")) {
			if (pastText.equals("/addPlace")) {
				int findChar = msg.indexOf('-');
				StringBuffer msgBuffer = new StringBuffer(msg);
				int startIndexForCity = 1;
				int endIndexForCity = findChar - 1;
				char[] bufferCity = new char[endIndexForCity - startIndexForCity];
				msgBuffer.getChars(startIndexForCity, endIndexForCity, bufferCity, 0);
				String city = new String(bufferCity);
				CityDto cityDto = controller.findCity(city);

				if (cityDto.getCity() != null) {
					int startIndexForPlace = findChar + 2;
					int endIndexForPlace = msgBuffer.length();
					char[] bufferPlace = new char[endIndexForPlace - startIndexForPlace];
					msgBuffer.getChars(startIndexForPlace, endIndexForPlace, bufferPlace, 0);
					String place = new String(bufferPlace);
					PlaceDto placeDto = controller.findPlaceFromCity(cityDto.getCity());
					if (placeDto == null) {
						controller.savePlace(cityDto, place);
						pastText = "";
						return "Place saved";
					} else {
						return "The place already exists";
					}
				}
				pastText = "";
				return "Incorrect text format";
			}
		}

		/*
		 * This method is used to save city to the database.
		 */
		if (msg.contains("/")) {
			if (pastText.equals("/addCity")) {
				StringBuffer cityBuffer = new StringBuffer(msg.trim());
				String cityString = cityBuffer.deleteCharAt(0).toString();
				for (CityDto cityDto : cityDtoList) {
					if (cityString.equals(cityDto.getCity())) {
						pastText = "";
						return "Such a city has already been added";
					}
				}
				controller.saveCity(cityString);
				pastText = "";
				return cityString + " - Added";
			}
		}


		return null;
	}
}
