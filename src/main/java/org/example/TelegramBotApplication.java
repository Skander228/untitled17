package org.example;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.BotCommand;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.*;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.request.SetMyCommands;
import java.util.Optional;

public class TelegramBotApplication extends TelegramBot {

    @lombok.Builder
    public TelegramBotApplication(String botToken) {
        super(botToken);
    }

    public void run() {
        this.setUpdatesListener(updates -> {
            updates.forEach(this::process);
            return UpdatesListener.CONFIRMED_UPDATES_ALL;
        });
    }

    private void process(Update update) {
        Message message = update.message();
        if (message != null) {
            String text = message.text();
            Optional.ofNullable(text).ifPresent(commandName -> this.serveCommand(commandName, message.chat().id()));
        }
    }

    private void serveCommand(String commandName, Long chatId) {

        var message = new SetMyCommands(
                new BotCommand("/start", "start bot"),
                new BotCommand("/help", "help")
        );
        this.execute(message);

        switch (commandName) {
            case "/start": {
                var response = new SendMessage(chatId, "Здравствуйте, дорогой собеседник! Меня зовут Пророст," +
                        " я чат-бот, и я предлагаю Вам пройти по пути поиска себя и своего предназначения в мире" +
                        " человеческих профессий. Вам потребуется делать выбор.");

                var response1 = new SendMessage(chatId, "Сначала – вслепую, руководствуясь только сердцем " +
                        "(или интуицией), затем – рационально. На любом шаге можно будет развернуться и выбрать" +
                        " другую дорожку. Сначала я буду предлагать варианты, и только потом рассказывать, что за" +
                        " ними стоит. Это обычное дело: принимая решения, человек никогда полностью не представляет," +
                        " что они несут, пусть в целом картина и видится ясной.");

                var response2 = new SendMessage(chatId, "Я предлагаю выбирать то, что ближе по духу. Первое," +
                        " что придёт на ум и понравится. И дойти до конца пути хотя бы из любопытства – а куда меня," +
                        " такого неопределившегося, или, наоборот, такого состоявшегося, выведет внутреннее чувство.");


                response.replyMarkup(new ReplyKeyboardMarkup("Согласен ", "Расскажи немного о том, что меня ждет.").resizeKeyboard(true));

                this.execute(response);
                this.execute(response1);
                this.execute(response2);
                break;
            }

            case "Расскажи немного о том, что меня ждет.": {
                var response = new SendMessage(chatId, "Всё началось с желания ответить на вопрос: " +
                        "«Как человек ищет себя?», который тут же родил следующий: «Как сделать поиск более" +
                        " искренним, точным и ведущим к счастью?». Чтобы не вязнуть в философии, сразу скажу," +
                        " что «счастье» для меня – это чувство полноты жизни, единства со всем миром при чётком " +
                        "(пусть даже бессловесном) понимании своего места в нём. Можно ли стать счастливым, только " +
                        "выбрав подходящую профессию? Думаю, нет. Но можно серьёзно продвинуться к желанному состоянию." +
                        "Так появилась группа разработки ассоциативного маршрутизатора «Пророст». Мы взяли международные " +
                        "справочники-классификации профессий. Перечитали и рассортировали все профессии в соответствии" +
                        " с более глубинными типами призваний. По импровизировали даже на тему индийских каст и европейских" +
                        " архетипов. Свели всё в более адекватную современному миру классификацию, выделив те общие корневые" +
                        " устремления типов людей, на которые есть запрос в настоящем и будущем мира оплачиваемых человеческих " +
                        "занятий. Проложили пути на карте.На данный момент и Вы, и я, и все причастные к разработке " +
                        "находимся в ситуации Колумба. Нам предстоит открыть новые земли, которые (конечно же) были и до" +
                        " нас, и некоторые даже были освоены и заселены. Однако и Вы, и мы, - все равно первопроходцы.");

                var response1 = new SendMessage(chatId, "В качестве входных параметров Вы получите поле ассоциаций," +
                        " утверждённых и формализованных в системе «Пророст», единое для всех участников системы." +
                        " Вы пройдёте полностью автоматизированный опросник с закрытой формой ответов на вопросы " +
                        "(выбор одного или нескольких вариантов из списка) и получите рекомендацию в форме конкретной " +
                        "профессии и прилагающихся к ней требований согласно международным справочникам. Это будет Ваш " +
                        "результат тестирования и повод попробовать постажироваться в новой роли, завести хобби или снова" +
                        " утвердиться в своём профессиональном выборе.");


                response.replyMarkup(new ReplyKeyboardMarkup(
                        new String[] {"Понятно. Отлично, я готов (а)!", "Понятно. Странно, но я готов (а)!"},
                        new String[] {"Ничего непонятно. Но интересно. Давайте попробуем.", "Ничего непонятно. Но, может быть, если я пройду этот опросник, вы улучшите систему."}
                ));

                this.execute(response);
                this.execute(response1);
                break;
            }

            case "Понятно. Отлично, я готов (а)!", "Согласен", "Ничего непонятно. Но, может быть, если я пройду этот опросник, вы улучшите систему.", "Ничего непонятно. Но интересно. Давайте попробуем.", "Понятно. Странно, но я готов (а)!": {
                var response = new SendMessage(chatId, "Уважаемый собеседник, выберите то, к чему больше расположены." +
                        " Пока что один вариант. После завершения одного пути Вы можете вновь оказаться" +
                        " на этой развилке и пройти по-другому, никто не ограничивает Вас в свободе выбора," +
                        " только в последовательности.");

                response.replyMarkup(new ReplyKeyboardMarkup(
                        new String[] {"Служение", "Защита"},
                        new String[] {"Хранение", "Коммуникации", "Инновации"}
                ));

                this.execute(response);
                break;
            }

            case "Служение": {
                var response = new SendMessage(chatId, "Вы выбрали Служение. Такой выбор делают люди," +
                        " чьим душам близки благородство, милосердие, долг, честь, способность поставить " +
                        "интересы общества, государства, идеи выше собственных интересов. Это значит, что " +
                        "Вам подойдут следующие области деятельности (к слову, Вам не обязательно трудиться" +
                        " именно на ключевых ролях – возможно, в общей команде ждут именно Вашей помощи в общем деле):");

                response.replyMarkup(new ReplyKeyboardMarkup(
                        new String[] {"Религиозное служение", "Дипломатическая служба"},
                        new String[] {"Государственная служба", "Культура"}
                ));

                this.execute(response);
                break;
            }

            case "Религиозное служение": {
                var response = new SendMessage(chatId, "Религиозное служение – чрезвычайно ответственное дело," +
                        " которое подходит далеко не всем. Есть множество рисков (например, быстрое эмоциональное" +
                        " выгорание, конфликты в общинах, жёсткая структура управления конфессиональными организациями)," +
                        " необходимость соответствовать идеалам, о которых говорит служитель, которым принадлежит." +
                        " Оно требует постоянного взгляда вверх, постоянного взгляда внутрь, чистого сердца и любви" +
                        " к людям(даже если Вы не общительны). Что для Вас важнее в этой сфере?");

                response.replyMarkup(new ReplyKeyboardMarkup(
                        new String[] {"Священнодействие и проповедь", "Поддержка знаний", "Экстрасенсорика",},
                        new String[] {"Организационная поддержка", "Помощь по хозяйству"},
                        new String[] {"Служение"}
                ));

                this.execute(response);
                break;
            }

            case "Священнодействие и проповедь": {
                var response = new SendMessage(chatId, "Священнодействие и проповедь – ядро религиозного служения." +
                        " Не ограниченное конфессией. Вы можете выбрать профессию, а я расскажу, какие основные " +
                        "обязанности к ней прилагаются согласно международным справочникам и чего от Вас ожидают" +
                        " на этом месте работы.");

                response.replyMarkup(new ReplyKeyboardMarkup(
                        new String[] {"Архиерей", "Муфтий", "Имам",},
                        new String[] {"Лама", "Раввин", "Священник"},
                        new String[] {"Пастор", "Пресвитер", "Дьякон"},
                        new String[] {"Мулла", "Монах", "Бхикшу"},
                        new String[] {"Настоятель монастыря", "Игумен", "Пономарь"},
                        new String[] {"Чтец","Служащий культовых организаций, не имеющий духовного сана"},
                        new String[] {"Религиозное служение"}
                ));

                this.execute(response);
                break;
            }
            case "Архиерей": {
                var response = new SendMessage(chatId, "Архиерей – служитель высшего чина православной церкви." +
                        " При этом архиереи могут быть по сану епископами, архиепископами, митрополитами, католикосами" +
                        " (в Грузинской Православной Церкви), патриархами. Сан назначает Церковь, человек не может " +
                        "сам его выбрать. В настоящей церковной практике Архиерей должен быть монахом. От Архиерея" +
                        " требуются: следование догматам и принципам вероучения, соблюдение обрядовой дисциплины и" +
                        " моральных принципов вероучения, совершение богослужений, религиозных обрядов, духовное " +
                        "руководство, наставление верующих, чтение проповедей, руководство национальной или " +
                        "территориальной религиозной общиной, руководство учебными курсами и программами религиозного " +
                        "образования, представительство в СМИ, государственных органах и т.д.от имени национальной или " +
                        "территориальной общины.");

                var response1 = new SendMessage(chatId, "Благодарю Вас за Ваш выбор. А теперь – три самых важных" +
                        " вопроса... по ссылке");

                var response2 = new SendMessage(chatId, "Для возвращения назад кликните ниже!");

                response1.replyMarkup(new InlineKeyboardMarkup(
                        new InlineKeyboardButton("Ссылка").url("https://docs.google.com/forms/d/e/1FAIpQLSfbj8-v" +
                                "FREt7d9V0iNgY4LSoe2YWnHFndF7GbehyNzI2mvkAw/viewform")
                ));

                response.replyMarkup(new ReplyKeyboardMarkup(
                        new KeyboardButton("Священнодействие и проповедь")
                ));

                this.execute(response);
                this.execute(response1);
                this.execute(response2);
                break;
            }
            case "Муфтий": {
                var response = new SendMessage(chatId, "Муфтий – высший чин в исламе, наделённый правом выносить решения по религиозно-юридическим вопросам, давать разъяснения по применению шариата. От Муфтия требуются: следование догматам и принципам вероучения, соблюдение обрядовой дисциплины и моральных принципов вероучения, совершение богослужений, религиозных обрядов, духовное руководство, наставление верующих, чтение проповедей, руководство национальной или территориальной религиозной общиной, руководство учебными курсами и программами религиозного образования, представительство в СМИ, государственных органах и т.д. от имени национальной или территориальной общины");

                var response1 = new SendMessage(chatId, "Благодарю Вас за Ваш выбор. А теперь – три самых важных" +
                        " вопроса... по ссылке");

                var response2 = new SendMessage(chatId, "Для возвращения назад кликните ниже!");

                response1.replyMarkup(new InlineKeyboardMarkup(
                        new InlineKeyboardButton("Ссылка").url("https://docs.google.com/forms/d/e/1FAIpQLSfbj8-v" +
                                "FREt7d9V0iNgY4LSoe2YWnHFndF7GbehyNzI2mvkAw/viewform")
                ));

                response.replyMarkup(new ReplyKeyboardMarkup(
                        new KeyboardButton("Священнодействие и проповедь")
                ));

                this.execute(response);
                this.execute(response1);
                this.execute(response2);
                break;

            }

            case "Имам": {
                var response = new SendMessage(chatId, "Имам – это предводитель молитвы и духовное лицо, которое заведует мечетью; титул, который носят наиболее выдающиеся богословы и религиозные авторитеты; духовный и светский глава всего мусульманского сообщества. От Имама требуются: следование догматам и принципам вероучения, соблюдение обрядовой дисциплины и моральных принципов вероучения, совершение богослужений, религиозных обрядов, духовное руководство, наставление верующих, чтение проповедей, руководство национальной или территориальной религиозной общиной, руководство учебными курсами и программами религиозного образования, представительство в СМИ, государственных органах и т.д. от имени национальной или территориальной общины");

                var response1 = new SendMessage(chatId, "Благодарю Вас за Ваш выбор. А теперь – три самых важных" +
                        " вопроса... по ссылке");

                var response2 = new SendMessage(chatId, "Для возвращения назад кликните ниже!");

                response1.replyMarkup(new InlineKeyboardMarkup(
                        new InlineKeyboardButton("Ссылка").url("https://docs.google.com/forms/d/e/1FAIpQLSfbj8-v" +
                                "FREt7d9V0iNgY4LSoe2YWnHFndF7GbehyNzI2mvkAw/viewform")
                ));

                response.replyMarkup(new ReplyKeyboardMarkup(
                        new KeyboardButton("Священнодействие и проповедь")
                ));

                this.execute(response);
                this.execute(response1);
                this.execute(response2);
                break;
            }

            case "Лама": {
                var response = new SendMessage(chatId, "Лама – религиозный учитель в буддизме. От Ламы требуются: следование догматам и принципам вероучения, соблюдение обрядовой дисциплины и моральных принципов вероучения, совершение богослужений, религиозных обрядов, духовное руководство, наставление верующих, чтение проповедей, руководство национальной или территориальной религиозной общиной, руководство учебными курсами и программами религиозного образования, представительство в СМИ, государственных органах и т.д. от имени национальной или территориальной общины");

                var response1 = new SendMessage(chatId, "Благодарю Вас за Ваш выбор. А теперь – три самых важных" +
                        " вопроса... по ссылке");

                var response2 = new SendMessage(chatId, "Для возвращения назад кликните ниже!");

                response1.replyMarkup(new InlineKeyboardMarkup(
                        new InlineKeyboardButton("Ссылка").url("https://docs.google.com/forms/d/e/1FAIpQLSfbj8-v" +
                                "FREt7d9V0iNgY4LSoe2YWnHFndF7GbehyNzI2mvkAw/viewform")
                ));

                response.replyMarkup(new ReplyKeyboardMarkup(
                        new KeyboardButton("Священнодействие и проповедь")
                ));

                this.execute(response);
                this.execute(response1);
                this.execute(response2);
                break;
            }

            case "Раввин": {
                var response = new SendMessage(chatId, "Раввин – высшее духовное лицо в иудаизме. От Раввина требуются: следование догматам и принципам вероучения, соблюдение обрядовой дисциплины и моральных принципов вероучения, совершение богослужений, религиозных обрядов, духовное руководство, наставление верующих, чтение проповедей, руководство национальной или территориальной религиозной общиной, руководство учебными курсами и программами религиозного образования, представительство в СМИ, государственных органах и т.д. от имени национальной или территориальной общины");

                var response1 = new SendMessage(chatId, "Благодарю Вас за Ваш выбор. А теперь – три самых важных" +
                        " вопроса... по ссылке");

                var response2 = new SendMessage(chatId, "Для возвращения назад кликните ниже!");

                response1.replyMarkup(new InlineKeyboardMarkup(
                        new InlineKeyboardButton("Ссылка").url("https://docs.google.com/forms/d/e/1FAIpQLSfbj8-v" +
                                "FREt7d9V0iNgY4LSoe2YWnHFndF7GbehyNzI2mvkAw/viewform")
                ));

                response.replyMarkup(new ReplyKeyboardMarkup(
                        new KeyboardButton("Священнодействие и проповедь")
                ));

                this.execute(response);
                this.execute(response1);
                this.execute(response2);
                break;
            }

            case "Священник": {
                var response = new SendMessage(chatId, "Священник: следование догматам и принципам вероучения, соблюдение обрядовой дисциплины и моральных принципов вероучения, совершение богослужений, религиозных обрядов или участие в них, духовное руководство, наставление верующих, чтение проповедей, руководство административной и хозяйственной деятельностью на приходе, в общине, руководство общественной деятельностью на приходе, в общине (образование, помощь нуждающимся, работа с молодежью и детьми), взаимодействие с государственными структурами, о СМИ и т.д");

                var response1 = new SendMessage(chatId, "Благодарю Вас за Ваш выбор. А теперь – три самых важных" +
                        " вопроса... по ссылке");

                var response2 = new SendMessage(chatId, "Для возвращения назад кликните ниже!");

                response1.replyMarkup(new InlineKeyboardMarkup(
                        new InlineKeyboardButton("Ссылка").url("https://docs.google.com/forms/d/e/1FAIpQLSfbj8-v" +
                                "FREt7d9V0iNgY4LSoe2YWnHFndF7GbehyNzI2mvkAw/viewform")
                ));

                response.replyMarkup(new ReplyKeyboardMarkup(
                        new KeyboardButton("Священнодействие и проповедь")
                ));

                this.execute(response);
                this.execute(response1);
                this.execute(response2);
                break;
            }

            case "Пастор": {
                var response = new SendMessage(chatId, "Пастор: следование догматам и принципам вероучения, соблюдение обрядовой дисциплины и моральных принципов вероучения, совершение богослужений, религиозных обрядов или участие в них, духовное руководство, наставление верующих, чтение проповедей, руководство административной и хозяйственной деятельностью на приходе, в общине, руководство общественной деятельностью на приходе, в общине (образование, помощь нуждающимся, работа с молодежью и детьми), взаимодействие с государственными структурами, о СМИ и т.д.");

                var response1 = new SendMessage(chatId, "Благодарю Вас за Ваш выбор. А теперь – три самых важных" +
                        " вопроса... по ссылке");

                var response2 = new SendMessage(chatId, "Для возвращения назад кликните ниже!");

                response1.replyMarkup(new InlineKeyboardMarkup(
                        new InlineKeyboardButton("Ссылка").url("https://docs.google.com/forms/d/e/1FAIpQLSfbj8-v" +
                                "FREt7d9V0iNgY4LSoe2YWnHFndF7GbehyNzI2mvkAw/viewform")
                ));

                response.replyMarkup(new ReplyKeyboardMarkup(
                        new KeyboardButton("Священнодействие и проповедь")
                ));

                this.execute(response);
                this.execute(response1);
                this.execute(response2);
                break;
            }

            case "Пресвитер": {
                var response = new SendMessage(chatId, "Пресвитер: следование догматам и принципам вероучения, соблюдение обрядовой дисциплины и моральных принципов вероучения, совершение богослужений, религиозных обрядов или участие в них, духовное руководство, наставление верующих, чтение проповедей, руководство административной и хозяйственной деятельностью на приходе, в общине, руководство общественной деятельностью на приходе, в общине (образование, помощь нуждающимся, работа с молодежью и детьми), взаимодействие с государственными структурами, о СМИ и т.д.");

                var response1 = new SendMessage(chatId, "Благодарю Вас за Ваш выбор. А теперь – три самых важных" +
                        " вопроса... по ссылке");

                var response2 = new SendMessage(chatId, "Для возвращения назад кликните ниже!");

                response1.replyMarkup(new InlineKeyboardMarkup(
                        new InlineKeyboardButton("Ссылка").url("https://docs.google.com/forms/d/e/1FAIpQLSfbj8-v" +
                                "FREt7d9V0iNgY4LSoe2YWnHFndF7GbehyNzI2mvkAw/viewform")
                ));

                response.replyMarkup(new ReplyKeyboardMarkup(
                        new KeyboardButton("Священнодействие и проповедь")
                ));

                this.execute(response);
                this.execute(response1);
                this.execute(response2);
                break;
            }

            case "Дьякон": {
                var response = new SendMessage(chatId, "Дьякон: следование догматам и принципам вероучения, соблюдение обрядовой дисциплины и моральных принципов вероучения, совершение богослужений, религиозных обрядов или участие в них, духовное руководство, наставление верующих, чтение проповедей, руководство административной и хозяйственной деятельностью на приходе, в общине, руководство общественной деятельностью на приходе, в общине (образование, помощь нуждающимся, работа с молодежью и детьми), взаимодействие с государственными структурами, о СМИ и т.д.");

                var response1 = new SendMessage(chatId, "Благодарю Вас за Ваш выбор. А теперь – три самых важных" +
                        " вопроса... по ссылке");

                var response2 = new SendMessage(chatId, "Для возвращения назад кликните ниже!");

                response1.replyMarkup(new InlineKeyboardMarkup(
                        new InlineKeyboardButton("Ссылка").url("https://docs.google.com/forms/d/e/1FAIpQLSfbj8-v" +
                                "FREt7d9V0iNgY4LSoe2YWnHFndF7GbehyNzI2mvkAw/viewform")
                ));

                response.replyMarkup(new ReplyKeyboardMarkup(
                        new KeyboardButton("Священнодействие и проповедь")
                ));

                this.execute(response);
                this.execute(response1);
                this.execute(response2);
                break;
            }

            case "Мулла": {
                var response = new SendMessage(chatId, "Мулла: следование догматам и принципам вероучения, соблюдение обрядовой дисциплины и моральных принципов вероучения, совершение богослужений, религиозных обрядов или участие в них, духовное руководство, наставление верующих, чтение проповедей, руководство административной и хозяйственной деятельностью на приходе, в общине, руководство общественной деятельностью на приходе, в общине (образование, помощь нуждающимся, работа с молодежью и детьми), взаимодействие с государственными структурами, о СМИ и т.д.");

                var response1 = new SendMessage(chatId, "Благодарю Вас за Ваш выбор. А теперь – три самых важных" +
                        " вопроса... по ссылке");

                var response2 = new SendMessage(chatId, "Для возвращения назад кликните ниже!");

                response1.replyMarkup(new InlineKeyboardMarkup(
                        new InlineKeyboardButton("Ссылка").url("https://docs.google.com/forms/d/e/1FAIpQLSfbj8-v" +
                                "FREt7d9V0iNgY4LSoe2YWnHFndF7GbehyNzI2mvkAw/viewform")
                ));

                response.replyMarkup(new ReplyKeyboardMarkup(
                        new KeyboardButton("Священнодействие и проповедь")
                ));

                this.execute(response);
                this.execute(response1);
                this.execute(response2);
                break;
            }

            case "Монах": {
                var response = new SendMessage(chatId, "Монах: следование догматам и принципам вероучения, изоляция от мирского общества, соблюдение обрядовой дисциплины, соблюдение моральных принципов вероучения, совершение богослужений, религиозных обрядов или участие в них, наставление верующих, чтение религиозных проповедей, участие в административной и хозяйственной деятельности монастыря");

                var response1 = new SendMessage(chatId, "Благодарю Вас за Ваш выбор. А теперь – три самых важных" +
                        " вопроса... по ссылке");

                var response2 = new SendMessage(chatId, "Для возвращения назад кликните ниже!");

                response1.replyMarkup(new InlineKeyboardMarkup(
                        new InlineKeyboardButton("Ссылка").url("https://docs.google.com/forms/d/e/1FAIpQLSfbj8-v" +
                                "FREt7d9V0iNgY4LSoe2YWnHFndF7GbehyNzI2mvkAw/viewform")
                ));

                response.replyMarkup(new ReplyKeyboardMarkup(
                        new KeyboardButton("Священнодействие и проповедь")
                ));

                this.execute(response);
                this.execute(response1);
                this.execute(response2);
                break;
            }

            case "Бхикшу": {
                var response = new SendMessage(chatId, "Бхикшу: следование догматам и принципам вероучения, изоляция от мирского общества, соблюдение обрядовой дисциплины, соблюдение моральных принципов вероучения, совершение богослужений, религиозных обрядов или участие в них, наставление верующих, чтение религиозных проповедей, участие в административной и хозяйственной деятельности монастыря");

                var response1 = new SendMessage(chatId, "Благодарю Вас за Ваш выбор. А теперь – три самых важных" +
                        " вопроса... по ссылке");

                var response2 = new SendMessage(chatId, "Для возвращения назад кликните ниже!");

                response1.replyMarkup(new InlineKeyboardMarkup(
                        new InlineKeyboardButton("Ссылка").url("https://docs.google.com/forms/d/e/1FAIpQLSfbj8-v" +
                                "FREt7d9V0iNgY4LSoe2YWnHFndF7GbehyNzI2mvkAw/viewform")
                ));

                response.replyMarkup(new ReplyKeyboardMarkup(
                        new KeyboardButton("Священнодействие и проповедь")
                ));

                this.execute(response);
                this.execute(response1);
                this.execute(response2);
                break;
            }

            case "Настоятель монастыря": {
                var response = new SendMessage(chatId, "Настоятель монастыря: следование догматам и принципам вероучения, изоляция от мирского общества, соблюдение обрядовой дисциплины, соблюдение моральных принципов вероучения, совершение богослужений, религиозных обрядов, духовное руководство, наставление верующих, чтение религиозных проповедей, руководство административной деятельностью монастыря, руководство хозяйственной деятельностью");

                var response1 = new SendMessage(chatId, "Благодарю Вас за Ваш выбор. А теперь – три самых важных" +
                        " вопроса... по ссылке");

                var response2 = new SendMessage(chatId, "Для возвращения назад кликните ниже!");

                response1.replyMarkup(new InlineKeyboardMarkup(
                        new InlineKeyboardButton("Ссылка").url("https://docs.google.com/forms/d/e/1FAIpQLSfbj8-v" +
                                "FREt7d9V0iNgY4LSoe2YWnHFndF7GbehyNzI2mvkAw/viewform")
                ));

                response.replyMarkup(new ReplyKeyboardMarkup(
                        new KeyboardButton("Священнодействие и проповедь")
                ));

                this.execute(response);
                this.execute(response1);
                this.execute(response2);
                break;
            }

            case "Игумен": {
                var response = new SendMessage(chatId, "Игумен: следование догматам и принципам вероучения, изоляция от мирского общества, соблюдение обрядовой дисциплины, соблюдение моральных принципов вероучения, совершение богослужений, религиозных обрядов, духовное руководство, наставление верующих, чтение религиозных проповедей, руководство административной деятельностью монастыря, руководство хозяйственной деятельностью");

                var response1 = new SendMessage(chatId, "Благодарю Вас за Ваш выбор. А теперь – три самых важных" +
                        " вопроса... по ссылке");

                var response2 = new SendMessage(chatId, "Для возвращения назад кликните ниже!");

                response1.replyMarkup(new InlineKeyboardMarkup(
                        new InlineKeyboardButton("Ссылка").url("https://docs.google.com/forms/d/e/1FAIpQLSfbj8-v" +
                                "FREt7d9V0iNgY4LSoe2YWnHFndF7GbehyNzI2mvkAw/viewform")
                ));

                response.replyMarkup(new ReplyKeyboardMarkup(
                        new KeyboardButton("Священнодействие и проповедь")
                ));

                this.execute(response);
                this.execute(response1);
                this.execute(response2);
                break;
            }

            case "Служащий культовых организаций, не имеющий духовного сана": {
                var response = new SendMessage(chatId, "Служащий культовых организаций, не имеющий духовного сана: следование догматам и принципам вероучения, участие в богослужении, помощь при проведении религиозных обрядов, моральная поддержка людей, участие в программах помощи нуждающимся, ответы на вопросы о содержании и смысле обрядов, ответы на вопросы о поведении во время обрядов, уборка алтаря");

                var response1 = new SendMessage(chatId, "Благодарю Вас за Ваш выбор. А теперь – три самых важных" +
                        " вопроса... по ссылке");

                var response2 = new SendMessage(chatId, "Для возвращения назад кликните ниже!");

                response1.replyMarkup(new InlineKeyboardMarkup(
                        new InlineKeyboardButton("Ссылка").url("https://docs.google.com/forms/d/e/1FAIpQLSfbj8-v" +
                                "FREt7d9V0iNgY4LSoe2YWnHFndF7GbehyNzI2mvkAw/viewform")
                ));

                response.replyMarkup(new ReplyKeyboardMarkup(
                        new KeyboardButton("Священнодействие и проповедь")
                ));

                this.execute(response);
                this.execute(response1);
                this.execute(response2);
                break;
            }

            case "Чтец": {
                var response = new SendMessage(chatId, "Чтец: следование догматам и принципам вероучения, участие в богослужении, помощь при проведении религиозных обрядов, моральная поддержка людей, участие в программах помощи нуждающимся, ответы на вопросы о содержании и смысле обрядов, ответы на вопросы о поведении во время обрядов, уборка алтаря");

                var response1 = new SendMessage(chatId, "Благодарю Вас за Ваш выбор. А теперь – три самых важных" +
                        " вопроса... по ссылке");

                var response2 = new SendMessage(chatId, "Для возвращения назад кликните ниже!");

                response1.replyMarkup(new InlineKeyboardMarkup(
                        new InlineKeyboardButton("Ссылка").url("https://docs.google.com/forms/d/e/1FAIpQLSfbj8-v" +
                                "FREt7d9V0iNgY4LSoe2YWnHFndF7GbehyNzI2mvkAw/viewform")
                ));

                response.replyMarkup(new ReplyKeyboardMarkup(
                        new KeyboardButton("Священнодействие и проповедь")
                ));

                this.execute(response);
                this.execute(response1);
                this.execute(response2);
                break;
            }

            case "Пономарь": {
                var response = new SendMessage(chatId, "Пономарь: следование догматам и принципам вероучения, участие в богослужении, помощь при проведении религиозных обрядов, моральная поддержка людей, участие в программах помощи нуждающимся, ответы на вопросы о содержании и смысле обрядов, ответы на вопросы о поведении во время обрядов, уборка алтаря");

                var response1 = new SendMessage(chatId, "Благодарю Вас за Ваш выбор. А теперь – три самых важных" +
                        " вопроса... по ссылке");

                var response2 = new SendMessage(chatId, "Для возвращения назад кликните ниже!");

                response1.replyMarkup(new InlineKeyboardMarkup(
                        new InlineKeyboardButton("Ссылка").url("https://docs.google.com/forms/d/e/1FAIpQLSfbj8-v" +
                                "FREt7d9V0iNgY4LSoe2YWnHFndF7GbehyNzI2mvkAw/viewform")
                ));

                response.replyMarkup(new ReplyKeyboardMarkup(
                        new KeyboardButton("Священнодействие и проповедь")
                ));

                this.execute(response);
                this.execute(response1);
                this.execute(response2);
                break;
            }

            case "Поддержка знаний": {
                var response = new SendMessage(chatId, "Поддержка знаний");

                response.replyMarkup(new ReplyKeyboardMarkup(
                        new String[] {"Теолог", "Проповедник, Катехизатор, Улем, Лама, Раввин"},
                        new String[] {"Преподаватель воскресной школы, Преподаватель медресе, Раввин"},
                        new String[] {"Преподаватель религиоведения в ВУЗе, Преподаватель теологии в ВУЗе"},
                        new String[] {"Преподаватель, доцент, профессор культурологии в ВУЗе"},
                        new String[] {"Преподаватель культурологии в колледже"},
                        new String[] {"Преподаватель философии, ВУЗ"},
                        new String[] {"Преподаватель философии, колледж"},
                        new String[] {"Заведующий детским садом"},
                        new String[] {"Воспитатель детского сада (яслей-сада)"},
                        new String[] {"Работник по присмотру за детьми во внешкольное время, Няня, Работник по уходу за детьми, Помощник воспитателя"},
                        new String[] {"Педагог дополнительного образования"},
                        new String[] {"Методист", "Архитектор религиозных зданий и сооружений"},
                        new String[] {"Дизайнер графических работ, Дизайнер полиграфических изданий, Художник-график"},
                        new String[] {"Дизайнер Web-сайтов"},
                        new String[] {"Специалист-техник по Web, Техник Web-сайта, Администратор Web-сайтов"},
                        new String[] {"Директор (заведующий) музея, Директор библиотека"},
                        new String[] {"Эксперт по комплектованию музейного и выставочного фонда, Специалист по учету музейных предметов , Хранитель (произведения искусства)"},
                        new String[] {"Библиотекарь", "Искусствовед", "Культуролог"},
                        new String[] {"Философ", "Журналист, Корреспондент"},
                        new String[] {"Главный редактор (издательства, редакции газет и журналов), Заведующий редакцией, Заведующий студией"},
                        new String[] {"Продюсер радио и телепрограмм, Режиссёр, Режиссер документальных фильмов, Режиссёр-постановщик, Продюсер телевизионных и кинофильмов, Продюсер телевизионных/ радионовостей"},
                        new String[] {"Ведущий программы, Диктор, Радиоведущий, Телеведущий"},
                        new String[] {"Фотограф"},
                        new String[] {"Религиозное служение"}
                ));

                this.execute(response);
                break;
            }

            case "Теолог": {
                var response = new SendMessage(chatId, "Требования: исследование и интерпретация религиозных концепций и теорий; изучение принципов, столпов, догматов религиозного вероучения; изучение истории религий; изучение первоисточников, касающихся религиозных феноменов; изучение современных религиозных движений; подготовка научной документации и отчетов; написание статей; участие в разработке учебных программ по теологии, религии и религиоведению");

                var response1 = new SendMessage(chatId, "Благодарю Вас за Ваш выбор. А теперь – три самых важных" +
                        " вопроса... по ссылке");

                var response2 = new SendMessage(chatId, "Для возвращения назад кликните ниже!");

                response1.replyMarkup(new InlineKeyboardMarkup(
                        new InlineKeyboardButton("Ссылка").url("https://docs.google.com/forms/d/e/1FAIpQLSfbj8-v" +
                                "FREt7d9V0iNgY4LSoe2YWnHFndF7GbehyNzI2mvkAw/viewform")
                ));

                response.replyMarkup(new ReplyKeyboardMarkup(
                        new KeyboardButton("Поддержка знаний")
                ));

                this.execute(response);
                this.execute(response1);
                this.execute(response2);
                break;
            }

            case "Проповедник, Катехизатор, Улем, Лама, Раввин": {
                var response = new SendMessage(chatId, "Требования: следование догматам и принципам вероучения; проповедь религиозного вероучения; обучение основам веры; объяснение основ духовной жизни; объяснение основ религиозной морали; моральная поддержка людей; ответы на вопросы о смысле и содержании обрядов; толкование священных книг");

                var response1 = new SendMessage(chatId, "Благодарю Вас за Ваш выбор. А теперь – три самых важных" +
                        " вопроса... по ссылке");

                var response2 = new SendMessage(chatId, "Для возвращения назад кликните ниже!");

                response1.replyMarkup(new InlineKeyboardMarkup(
                        new InlineKeyboardButton("Ссылка").url("https://docs.google.com/forms/d/e/1FAIpQLSfbj8-v" +
                                "FREt7d9V0iNgY4LSoe2YWnHFndF7GbehyNzI2mvkAw/viewform")
                ));

                response.replyMarkup(new ReplyKeyboardMarkup(
                        new KeyboardButton("Поддержка знаний")
                ));

                this.execute(response);
                this.execute(response1);
                this.execute(response2);
                break;
            }

            case "Преподаватель воскресной школы, Преподаватель медресе, Раввин": {
                var response = new SendMessage(chatId, "Требования: разработка и изменение учебных программ по вероучительным дисциплинам; проведение учебных занятий по основам веры; проведение учебных занятий по основам обрядовой практики; проведение учебных занятий по основам религиозного искусства; религиозное воспитание; проведение контрольных работ; побуждение учащихся к размышлениям; написание учебных пособий, статей");

                var response1 = new SendMessage(chatId, "Благодарю Вас за Ваш выбор. А теперь – три самых важных" +
                        " вопроса... по ссылке");

                var response2 = new SendMessage(chatId, "Для возвращения назад кликните ниже!");

                response1.replyMarkup(new InlineKeyboardMarkup(
                        new InlineKeyboardButton("Ссылка").url("https://docs.google.com/forms/d/e/1FAIpQLSfbj8-v" +
                                "FREt7d9V0iNgY4LSoe2YWnHFndF7GbehyNzI2mvkAw/viewform")
                ));

                response.replyMarkup(new ReplyKeyboardMarkup(
                        new KeyboardButton("Поддержка знаний")
                ));

                this.execute(response);
                this.execute(response1);
                this.execute(response2);
                break;
            }

            case "Преподаватель религиоведения в ВУЗе, Преподаватель теологии в ВУЗе": {
                var response = new SendMessage(chatId, "Требования: разработка и изменение учебных программ по теологии и религиоведению; проведение учебных занятий; побуждение студентов к дискуссиям и независимым размышлениям; проверка и оценка контрольных работ и экзаменов; руководство научной работы студентов; проведение научных исследований; подготовка учебных изданий, пособий и статей; участие в научных конференциях и семинарах");

                var response1 = new SendMessage(chatId, "Благодарю Вас за Ваш выбор. А теперь – три самых важных" +
                        " вопроса... по ссылке");

                var response2 = new SendMessage(chatId, "Для возвращения назад кликните ниже!");

                response1.replyMarkup(new InlineKeyboardMarkup(
                        new InlineKeyboardButton("Ссылка").url("https://docs.google.com/forms/d/e/1FAIpQLSfbj8-v" +
                                "FREt7d9V0iNgY4LSoe2YWnHFndF7GbehyNzI2mvkAw/viewform")
                ));

                response.replyMarkup(new ReplyKeyboardMarkup(
                        new KeyboardButton("Поддержка знаний")
                ));

                this.execute(response);
                this.execute(response1);
                this.execute(response2);
                break;
            }

            case "Преподаватель, доцент, профессор культурологии в ВУЗе": {
                var response = new SendMessage(chatId, "Требования: разработка и изменение учебных программ по культурологии; проведение учебных занятий, в том числе по темам, касающимся религии как феномена культуры; побуждение студентов к дискуссиям и независимым размышлениям; проверка и оценка контрольных работ и экзаменов; руководство научной работы студентов, в том числе по религиозной проблематике; проведение научных исследований; подготовка учебных изданий, пособий и статей; участие в научных конференциях и семинарах");

                var response1 = new SendMessage(chatId, "Благодарю Вас за Ваш выбор. А теперь – три самых важных" +
                        " вопроса... по ссылке");

                var response2 = new SendMessage(chatId, "Для возвращения назад кликните ниже!");

                response1.replyMarkup(new InlineKeyboardMarkup(
                        new InlineKeyboardButton("Ссылка").url("https://docs.google.com/forms/d/e/1FAIpQLSfbj8-v" +
                                "FREt7d9V0iNgY4LSoe2YWnHFndF7GbehyNzI2mvkAw/viewform")
                ));

                response.replyMarkup(new ReplyKeyboardMarkup(
                        new KeyboardButton("Поддержка знаний")
                ));

                this.execute(response);
                this.execute(response1);
                this.execute(response2);
                break;
            }

            case "Преподаватель культурологии в колледже": {
                var response = new SendMessage(chatId, "Требования: разработка и изменение учебных программ по курсу культурологии; проведение учебных занятий, в том числе по темам, касающимся рассмотрения религии как части культуры; оценивание работы студентов; побуждение студентов к дискуссиям; ведение табелей успеваемости и посещаемости студентов; проведение, проверка и оценка контрольных работ и экзаменов; подготовка учебных изданий, пособий и статей; подготовка отчето");

                var response1 = new SendMessage(chatId, "Благодарю Вас за Ваш выбор. А теперь – три самых важных" +
                        " вопроса... по ссылке");

                var response2 = new SendMessage(chatId, "Для возвращения назад кликните ниже!");

                response1.replyMarkup(new InlineKeyboardMarkup(
                        new InlineKeyboardButton("Ссылка").url("https://docs.google.com/forms/d/e/1FAIpQLSfbj8-v" +
                                "FREt7d9V0iNgY4LSoe2YWnHFndF7GbehyNzI2mvkAw/viewform")
                ));

                response.replyMarkup(new ReplyKeyboardMarkup(
                        new KeyboardButton("Поддержка знаний")
                ));

                this.execute(response);
                this.execute(response1);
                this.execute(response2);
                break;
            }

            case "Преподаватель философии, ВУЗ": {
                var response = new SendMessage(chatId, "Требования: разработка и изменение учебных программ по философии; проведение учебных занятий, в том числе по темам философии религии; побуждение студентов к дискуссиям и независимым размышлениям; проверка и оценка контрольных работ и экзаменов; руководство научной работы студентов, в том числе по темам философии религии; проведение научных исследований; подготовка учебных изданий, пособий и статей по философии и философии религии; участие в научных конференциях и семинарах");

                var response1 = new SendMessage(chatId, "Благодарю Вас за Ваш выбор. А теперь – три самых важных" +
                        " вопроса... по ссылке");

                var response2 = new SendMessage(chatId, "Для возвращения назад кликните ниже!");

                response1.replyMarkup(new InlineKeyboardMarkup(
                        new InlineKeyboardButton("Ссылка").url("https://docs.google.com/forms/d/e/1FAIpQLSfbj8-v" +
                                "FREt7d9V0iNgY4LSoe2YWnHFndF7GbehyNzI2mvkAw/viewform")
                ));

                response.replyMarkup(new ReplyKeyboardMarkup(
                        new KeyboardButton("Поддержка знаний")
                ));

                this.execute(response);
                this.execute(response1);
                this.execute(response2);
                break;
            }

            case "Преподаватель философии, колледж": {
                var response = new SendMessage(chatId, "Требования: разработка и изменение учебных программ по курсу философии; проведение учебных занятий, в том числе по темам философии религии; оценивание работы студентов; побуждение студентов к дискуссиям; ведение табелей успеваемости и посещаемости студентов; проведение, проверка и оценка контрольных работ и экзаменов; подготовка учебных изданий, пособий и статей по философии и философии религии; подготовка отчетов");

                var response1 = new SendMessage(chatId, "Благодарю Вас за Ваш выбор. А теперь – три самых важных" +
                        " вопроса... по ссылке");

                var response2 = new SendMessage(chatId, "Для возвращения назад кликните ниже!");

                response1.replyMarkup(new InlineKeyboardMarkup(
                        new InlineKeyboardButton("Ссылка").url("https://docs.google.com/forms/d/e/1FAIpQLSfbj8-v" +
                                "FREt7d9V0iNgY4LSoe2YWnHFndF7GbehyNzI2mvkAw/viewform")
                ));

                response.replyMarkup(new ReplyKeyboardMarkup(
                        new KeyboardButton("Поддержка знаний")
                ));

                this.execute(response);
                this.execute(response1);
                this.execute(response2);
                break;
            }

            case "Заведующий детским садом": {
                var response = new SendMessage(chatId, "Требования: разработка программ развития (в том числе религиозного) дошкольников; наблюдение за развитием детей; планирование режима дня детей; создание безопасной среды для детей, персонала и посетителей; проведение бесед и собраний с родителями; подбор персонала с точки зрения профессиональных и личных качеств; контроль бюджета и расходов; составление отчетов");

                var response1 = new SendMessage(chatId, "Благодарю Вас за Ваш выбор. А теперь – три самых важных" +
                        " вопроса... по ссылке");

                var response2 = new SendMessage(chatId, "Для возвращения назад кликните ниже!");

                response1.replyMarkup(new InlineKeyboardMarkup(
                        new InlineKeyboardButton("Ссылка").url("https://docs.google.com/forms/d/e/1FAIpQLSfbj8-v" +
                                "FREt7d9V0iNgY4LSoe2YWnHFndF7GbehyNzI2mvkAw/viewform")
                ));

                response.replyMarkup(new ReplyKeyboardMarkup(
                        new KeyboardButton("Поддержка знаний")
                ));

                this.execute(response);
                this.execute(response1);
                this.execute(response2);
                break;
            }

            case "Воспитатель детского сада (яслей-сада)": {
                var response = new SendMessage(chatId, "Требования: организация деятельности детей с учетом содержания определенного вероучения и норм религиозной морали; планирование режима дня детей; обучение основам определенной религиозной веры; проведение с детьми творческих занятий; оценка развития детей; наблюдение за самочувствием и безопасностью детей; организация безопасной среды для занятий и игр; обсуждение успехов или проблем детей с родителями");

                var response1 = new SendMessage(chatId, "Благодарю Вас за Ваш выбор. А теперь – три самых важных" +
                        " вопроса... по ссылке");

                var response2 = new SendMessage(chatId, "Для возвращения назад кликните ниже!");

                response1.replyMarkup(new InlineKeyboardMarkup(
                        new InlineKeyboardButton("Ссылка").url("https://docs.google.com/forms/d/e/1FAIpQLSfbj8-v" +
                                "FREt7d9V0iNgY4LSoe2YWnHFndF7GbehyNzI2mvkAw/viewform")
                ));

                response.replyMarkup(new ReplyKeyboardMarkup(
                        new KeyboardButton("Поддержка знаний")
                ));

                this.execute(response);
                this.execute(response1);
                this.execute(response2);
                break;
            }

            case "Работник по присмотру за детьми во внешкольное время, Няня, Работник по уходу за детьми, Помощник воспитателя": {
                var response = new SendMessage(chatId, "Требования: помощь детям при купании, одевании и питании; помощь во время прогулок; игра с детьми; чтение книг или рассказывание историй; контроль поведения детей с учетом содержания определенного вероучения и норм религиозной морали; поддержание дисциплины; наблюдение за играми детей; подготовка помещения для игр или учебной деятельности");

                var response1 = new SendMessage(chatId, "Благодарю Вас за Ваш выбор. А теперь – три самых важных" +
                        " вопроса... по ссылке");

                var response2 = new SendMessage(chatId, "Для возвращения назад кликните ниже!");

                response1.replyMarkup(new InlineKeyboardMarkup(
                        new InlineKeyboardButton("Ссылка").url("https://docs.google.com/forms/d/e/1FAIpQLSfbj8-v" +
                                "FREt7d9V0iNgY4LSoe2YWnHFndF7GbehyNzI2mvkAw/viewform")
                ));

                response.replyMarkup(new ReplyKeyboardMarkup(
                        new KeyboardButton("Поддержка знаний")
                ));

                this.execute(response);
                this.execute(response1);
                this.execute(response2);
                break;
            }

            case "Педагог дополнительного образования": {
                var response = new SendMessage(chatId, "Требования: организация работы детских кружков и секций с учетом содержания определенного вероучения и норм религиозной морали; проведение учебных занятий для взрослых; разработка планов и графиков работы; обучение ремеслам; обучение богослужебному пению; обучение изобразительному искусству с учетом содержания определенного вероучения; развитие интересов, способностей; содействие саморазвитию");

                var response1 = new SendMessage(chatId, "Благодарю Вас за Ваш выбор. А теперь – три самых важных" +
                        " вопроса... по ссылке");

                var response2 = new SendMessage(chatId, "Для возвращения назад кликните ниже!");

                response1.replyMarkup(new InlineKeyboardMarkup(
                        new InlineKeyboardButton("Ссылка").url("https://docs.google.com/forms/d/e/1FAIpQLSfbj8-v" +
                                "FREt7d9V0iNgY4LSoe2YWnHFndF7GbehyNzI2mvkAw/viewform")
                ));

                response.replyMarkup(new ReplyKeyboardMarkup(
                        new KeyboardButton("Поддержка знаний")
                ));

                this.execute(response);
                this.execute(response1);
                this.execute(response2);
                break;
            }

            case "Методист": {
                var response = new SendMessage(chatId, "Требования: исследование изменений в стандартах на всех ступенях образования; исследование методик преподавания; консультирование по возможным улучшениям в учебных программах; составление учебных программ; консультирование по методам проведения экзаменов; организация повышения квалификации преподавателей; организация семинаров, конференций для обучения преподавателей новым программам и методам");

                var response1 = new SendMessage(chatId, "Благодарю Вас за Ваш выбор. А теперь – три самых важных" +
                        " вопроса... по ссылке");

                var response2 = new SendMessage(chatId, "Для возвращения назад кликните ниже!");

                response1.replyMarkup(new InlineKeyboardMarkup(
                        new InlineKeyboardButton("Ссылка").url("https://docs.google.com/forms/d/e/1FAIpQLSfbj8-v" +
                                "FREt7d9V0iNgY4LSoe2YWnHFndF7GbehyNzI2mvkAw/viewform")
                ));

                response.replyMarkup(new ReplyKeyboardMarkup(
                        new KeyboardButton("Поддержка знаний")
                ));

                this.execute(response);
                this.execute(response1);
                this.execute(response2);
                break;
            }

            case "Архитектор религиозных зданий и сооружений": {
                var response = new SendMessage(chatId, "Требования: разработка архитектурной концепции храма, мечети и др.; создание проекта и подготовка проектной документации для строительства; составление эскизов, рисунков и планов; расчет объема строительно-монтажных работ; расчет количества оборудования, изделий и материалов; консультации по поводу эксплуатации зданий; надзор за строительством проектируемых объектов; реставрация и реконструкция храмов");

                var response1 = new SendMessage(chatId, "Благодарю Вас за Ваш выбор. А теперь – три самых важных" +
                        " вопроса... по ссылке");

                var response2 = new SendMessage(chatId, "Для возвращения назад кликните ниже!");

                response1.replyMarkup(new InlineKeyboardMarkup(
                        new InlineKeyboardButton("Ссылка").url("https://docs.google.com/forms/d/e/1FAIpQLSfbj8-v" +
                                "FREt7d9V0iNgY4LSoe2YWnHFndF7GbehyNzI2mvkAw/viewform")
                ));

                response.replyMarkup(new ReplyKeyboardMarkup(
                        new KeyboardButton("Поддержка знаний")
                ));

                this.execute(response);
                this.execute(response1);
                this.execute(response2);
                break;
            }

            case "Дизайнер графических работ, Дизайнер полиграфических изданий, Художник-график": {
                var response = new SendMessage(chatId, "Требования: разработка дизайна газет и журналов религиозной организации; разработка дизайна книг, брошюр и т.д; разработка дизайна для плакатов, открыток и т.д. религиозной организации; создание эскизов, иллюстраций, оригинал-макетов; разработка графики и анимации для WEB-сайта; выбор материалов для полиграфической продукции; определение ограничений при реализации проектов; наблюдение за изготовлением конечного продукта");

                var response1 = new SendMessage(chatId, "Благодарю Вас за Ваш выбор. А теперь – три самых важных" +
                        " вопроса... по ссылке");

                var response2 = new SendMessage(chatId, "Для возвращения назад кликните ниже!");

                response1.replyMarkup(new InlineKeyboardMarkup(
                        new InlineKeyboardButton("Ссылка").url("https://docs.google.com/forms/d/e/1FAIpQLSfbj8-v" +
                                "FREt7d9V0iNgY4LSoe2YWnHFndF7GbehyNzI2mvkAw/viewform")
                ));

                response.replyMarkup(new ReplyKeyboardMarkup(
                        new KeyboardButton("Поддержка знаний")
                ));

                this.execute(response);
                this.execute(response1);
                this.execute(response2);
                break;
            }

            case "Дизайнер Web-сайтов": {
                var response = new SendMessage(chatId, "Требования: определение целей создания Web-сайта религиозной организации, храма, общины; определение функциональных требований к Web-сайту; участие в определении содержания сайта; определение эстетических требований к сайту; разработка дизайна для сайта религиозной организации; разработка графики и анимации для Web-сайта; создание и изменение Web-страниц; наблюдение за созданием конечного продукта");

                var response1 = new SendMessage(chatId, "Благодарю Вас за Ваш выбор. А теперь – три самых важных" +
                        " вопроса... по ссылке");

                var response2 = new SendMessage(chatId, "Для возвращения назад кликните ниже!");

                response1.replyMarkup(new InlineKeyboardMarkup(
                        new InlineKeyboardButton("Ссылка").url("https://docs.google.com/forms/d/e/1FAIpQLSfbj8-v" +
                                "FREt7d9V0iNgY4LSoe2YWnHFndF7GbehyNzI2mvkAw/viewform")
                ));

                response.replyMarkup(new ReplyKeyboardMarkup(
                        new KeyboardButton("Поддержка знаний")
                ));

                this.execute(response);
                this.execute(response1);
                this.execute(response2);
                break;
            }

            case "Специалист-техник по Web, Техник Web-сайта, Администратор Web-сайтов": {
                var response = new SendMessage(chatId, "Требования: поддержка функционирования Web-сайта; установка и обслуживание необходимого для Web-сайта программного обеспечения; проверка надежности интернет-соединения; программирование Web-страниц; создание и изменение Web-страниц; консультации для пользователей Web-сайта; выполнение Web-серверного резервного копирования и восстановления информации; улучшение функционала сайта");

                var response1 = new SendMessage(chatId, "Благодарю Вас за Ваш выбор. А теперь – три самых важных" +
                        " вопроса... по ссылке");

                var response2 = new SendMessage(chatId, "Для возвращения назад кликните ниже!");

                response1.replyMarkup(new InlineKeyboardMarkup(
                        new InlineKeyboardButton("Ссылка").url("https://docs.google.com/forms/d/e/1FAIpQLSfbj8-v" +
                                "FREt7d9V0iNgY4LSoe2YWnHFndF7GbehyNzI2mvkAw/viewform")
                ));

                response.replyMarkup(new ReplyKeyboardMarkup(
                        new KeyboardButton("Поддержка знаний")
                ));

                this.execute(response);
                this.execute(response1);
                this.execute(response2);
                break;
            }

            case "Директор (заведующий) музея, Директор библиотека": {
                var response = new SendMessage(chatId, "Требования: обеспечение общего руководства музеем, библиотекой; руководство персоналом; подбор и обучение персонала; планирование бюджета и учету расходов; составление общих отчетов о работе; составление коллекции предметов, представляющих интерес с религиозной точки зрения; составление и обслуживание собраний книг, периодики и других изданий религиозного содержания; пополнение коллекций книг, документов и материальных предметов");

                var response1 = new SendMessage(chatId, "Благодарю Вас за Ваш выбор. А теперь – три самых важных" +
                        " вопроса... по ссылке");

                var response2 = new SendMessage(chatId, "Для возвращения назад кликните ниже!");

                response1.replyMarkup(new InlineKeyboardMarkup(
                        new InlineKeyboardButton("Ссылка").url("https://docs.google.com/forms/d/e/1FAIpQLSfbj8-v" +
                                "FREt7d9V0iNgY4LSoe2YWnHFndF7GbehyNzI2mvkAw/viewform")
                ));

                response.replyMarkup(new ReplyKeyboardMarkup(
                        new KeyboardButton("Поддержка знаний")
                ));

                this.execute(response);
                this.execute(response1);
                this.execute(response2);
                break;
            }

            case "Эксперт по комплектованию музейного и выставочного фонда, Специалист по учету музейных предметов , Хранитель (произведения искусства)": {
                var response = new SendMessage(chatId, "Требования: составление коллекции предметов, представляющих интерес с религиозной точки зрения; организация хранения коллекции; пополнение коллекции; пополнение архива; составление каталогов и справочников по собранному материалу; организация экспозиций в музеях; подготовка научной документации и отчетов; поиск и подбор информации по запросам");

                var response1 = new SendMessage(chatId, "Благодарю Вас за Ваш выбор. А теперь – три самых важных" +
                        " вопроса... по ссылке");

                var response2 = new SendMessage(chatId, "Для возвращения назад кликните ниже!");

                response1.replyMarkup(new InlineKeyboardMarkup(
                        new InlineKeyboardButton("Ссылка").url("https://docs.google.com/forms/d/e/1FAIpQLSfbj8-v" +
                                "FREt7d9V0iNgY4LSoe2YWnHFndF7GbehyNzI2mvkAw/viewform")
                ));

                response.replyMarkup(new ReplyKeyboardMarkup(
                        new KeyboardButton("Поддержка знаний")
                ));

                this.execute(response);
                this.execute(response1);
                this.execute(response2);
                break;
            }

            case "Библиотекарь": {
                var response = new SendMessage(chatId, "Требования: обслуживание собраний книг, периодических изданий религиозной тематики; обслуживание собраний фильмов и аудиозаписей; анализ запросов читателей; рекомендации по приобретению книг; составление каталогов; выдача книг и др. материалов; обслуживание читального зала; поиск и подбор информации в материалах библиотеки");

                var response1 = new SendMessage(chatId, "Благодарю Вас за Ваш выбор. А теперь – три самых важных" +
                        " вопроса... по ссылке");

                var response2 = new SendMessage(chatId, "Для возвращения назад кликните ниже!");

                response1.replyMarkup(new InlineKeyboardMarkup(
                        new InlineKeyboardButton("Ссылка").url("https://docs.google.com/forms/d/e/1FAIpQLSfbj8-v" +
                                "FREt7d9V0iNgY4LSoe2YWnHFndF7GbehyNzI2mvkAw/viewform")
                ));

                response.replyMarkup(new ReplyKeyboardMarkup(
                        new KeyboardButton("Поддержка знаний")
                ));

                this.execute(response);
                this.execute(response1);
                this.execute(response2);
                break;
            }

            case "Искусствовед": {
                var response = new SendMessage(chatId, "Требования: исследования в области религиозного искусства; экспертиза произведений религиозного искусства; консультирование по вопросам реставрации; организация выставок; поиск относящегося к делу материала, проверка его подлинности; участие в разработке программ; учебных курсов; написание статей; подготовка научной документации и отчетов");

                var response1 = new SendMessage(chatId, "Благодарю Вас за Ваш выбор. А теперь – три самых важных" +
                        " вопроса... по ссылке");

                var response2 = new SendMessage(chatId, "Для возвращения назад кликните ниже!");

                response1.replyMarkup(new InlineKeyboardMarkup(
                        new InlineKeyboardButton("Ссылка").url("https://docs.google.com/forms/d/e/1FAIpQLSfbj8-v" +
                                "FREt7d9V0iNgY4LSoe2YWnHFndF7GbehyNzI2mvkAw/viewform")
                ));

                response.replyMarkup(new ReplyKeyboardMarkup(
                        new KeyboardButton("Поддержка знаний")
                ));

                this.execute(response);
                this.execute(response1);
                this.execute(response2);
                break;
            }

            case "Культуролог": {
                var response = new SendMessage(chatId, "Требования: исследование религии как феномена культуры; интерпретация религиозного опыта в контексте определенной культуры; изучение религиозных представлений в контексте современной культуры; разработка теорий, моделей и методов для объяснения существования религии в культуре; поиск относящегося к делу материала, проверка его подлинности; участие в разработке программ учебных курсов; представление результатов и выводов к публикации; подготовка научных статей");

                var response1 = new SendMessage(chatId, "Благодарю Вас за Ваш выбор. А теперь – три самых важных" +
                        " вопроса... по ссылке");

                var response2 = new SendMessage(chatId, "Для возвращения назад кликните ниже!");

                response1.replyMarkup(new InlineKeyboardMarkup(
                        new InlineKeyboardButton("Ссылка").url("https://docs.google.com/forms/d/e/1FAIpQLSfbj8-v" +
                                "FREt7d9V0iNgY4LSoe2YWnHFndF7GbehyNzI2mvkAw/viewform")
                ));

                response.replyMarkup(new ReplyKeyboardMarkup(
                        new KeyboardButton("Поддержка знаний")
                ));

                this.execute(response);
                this.execute(response1);
                this.execute(response2);
                break;
            }

            case "Философ": {
                var response = new SendMessage(chatId, "Требования: исследование наиболее общих понятий философии религии; интерпретация и развитие религиозно-философских концепций и теорий; поиск относящегося к делу материала, проверка его подлинности; разработка теорий и моделей для объяснения природы религиозного опыта; представление результатов и выводов к публикации; написание учебных пособий; участие в разработке программ учебных курсов; подготовка научных статей");

                var response1 = new SendMessage(chatId, "Благодарю Вас за Ваш выбор. А теперь – три самых важных" +
                        " вопроса... по ссылке");

                var response2 = new SendMessage(chatId, "Для возвращения назад кликните ниже!");

                response1.replyMarkup(new InlineKeyboardMarkup(
                        new InlineKeyboardButton("Ссылка").url("https://docs.google.com/forms/d/e/1FAIpQLSfbj8-v" +
                                "FREt7d9V0iNgY4LSoe2YWnHFndF7GbehyNzI2mvkAw/viewform")
                ));

                response.replyMarkup(new ReplyKeyboardMarkup(
                        new KeyboardButton("Поддержка знаний")
                ));

                this.execute(response);
                this.execute(response1);
                this.execute(response2);
                break;
            }

            case "Журналист, Корреспондент": {
                var response = new SendMessage(chatId, "Требования: сбор новостей, касающихся определенной религиозной организации для публикации в СМИ; проверка достоверности новостей и других документов; интервью с религиозными и общественными деятелями, записанные для радио, телевидения или Интернет-изданий; написание редакционных статей; подготовка комментариев по актуальным проблемам религиозной тематики; выбор материалов для публикации; проверка стиля, грамматики, точности содержания материалов; подготовка критических обзоров публикаций, произведений искусства и т.д.");

                var response1 = new SendMessage(chatId, "Благодарю Вас за Ваш выбор. А теперь – три самых важных" +
                        " вопроса... по ссылке");

                var response2 = new SendMessage(chatId, "Для возвращения назад кликните ниже!");

                response1.replyMarkup(new InlineKeyboardMarkup(
                        new InlineKeyboardButton("Ссылка").url("https://docs.google.com/forms/d/e/1FAIpQLSfbj8-v" +
                                "FREt7d9V0iNgY4LSoe2YWnHFndF7GbehyNzI2mvkAw/viewform")
                ));

                response.replyMarkup(new ReplyKeyboardMarkup(
                        new KeyboardButton("Поддержка знаний")
                ));

                this.execute(response);
                this.execute(response1);
                this.execute(response2);
                break;
            }

            case "Главный редактор (издательства, редакции газет и журналов), Заведующий редакцией, Заведующий студией": {
                var response = new SendMessage(chatId, "Требования: планирование производства видео- и телевизионных фильмов; планирование издательской деятельности; контроль соблюдения сроков производства и публикации; планирование бюджета и контроль расходов; заключение договоров с необходимыми поставщиками; руководство журналистами и техническим персоналом; координация повседневной деятельности");

                var response1 = new SendMessage(chatId, "Благодарю Вас за Ваш выбор. А теперь – три самых важных" +
                        " вопроса... по ссылке");

                var response2 = new SendMessage(chatId, "Для возвращения назад кликните ниже!");

                response1.replyMarkup(new InlineKeyboardMarkup(
                        new InlineKeyboardButton("Ссылка").url("https://docs.google.com/forms/d/e/1FAIpQLSfbj8-v" +
                                "FREt7d9V0iNgY4LSoe2YWnHFndF7GbehyNzI2mvkAw/viewform")
                ));

                response.replyMarkup(new ReplyKeyboardMarkup(
                        new KeyboardButton("Поддержка знаний")
                ));

                this.execute(response);
                this.execute(response1);
                this.execute(response2);
                break;
            }

            case "Продюсер радио и телепрограмм, Режиссёр, Режиссер документальных фильмов, Режиссёр-постановщик, Продюсер телевизионных и кинофильмов, Продюсер телевизионных/ радионовостей": {
                var response = new SendMessage(chatId, "Требования: руководство всеми этапами создания программ и фильмов религиозного содержания; планирование радио- и телепрограмм; создание сценариев для записи, видеозаписи; определение графиков производства программ и фильмов; финансовый контроль производства программ и фильмов; редактирование программ; руководство техническим персоналом; контроль реквизита, осветительной и звуковой аппаратуры");

                var response1 = new SendMessage(chatId, "Благодарю Вас за Ваш выбор. А теперь – три самых важных" +
                        " вопроса... по ссылке");

                var response2 = new SendMessage(chatId, "Для возвращения назад кликните ниже!");

                response1.replyMarkup(new InlineKeyboardMarkup(
                        new InlineKeyboardButton("Ссылка").url("https://docs.google.com/forms/d/e/1FAIpQLSfbj8-v" +
                                "FREt7d9V0iNgY4LSoe2YWnHFndF7GbehyNzI2mvkAw/viewform")
                ));

                response.replyMarkup(new ReplyKeyboardMarkup(
                        new KeyboardButton("Поддержка знаний")
                ));

                this.execute(response);
                this.execute(response1);
                this.execute(response2);
                break;
            }

            case "Ведущий программы, Диктор, Радиоведущий, Телеведущий": {
                var response = new SendMessage(chatId, "Требования: зачитывание новостей на радио или телевидении; ведение репортажей с места событий; проведение интервью по радио или телевидению; ведение радио- или телепередачи в прямом эфире; изучение справочных материалов для подготовки к программе или интервью; запись книг на электронные и цифровые носители; подготовка образовательных программ на радио и телевидении");

                var response1 = new SendMessage(chatId, "Благодарю Вас за Ваш выбор. А теперь – три самых важных" +
                        " вопроса... по ссылке");

                var response2 = new SendMessage(chatId, "Для возвращения назад кликните ниже!");

                response1.replyMarkup(new InlineKeyboardMarkup(
                        new InlineKeyboardButton("Ссылка").url("https://docs.google.com/forms/d/e/1FAIpQLSfbj8-v" +
                                "FREt7d9V0iNgY4LSoe2YWnHFndF7GbehyNzI2mvkAw/viewform")
                ));

                response.replyMarkup(new ReplyKeyboardMarkup(
                        new KeyboardButton("Поддержка знаний")
                ));

                this.execute(response);
                this.execute(response1);
                this.execute(response2);
                break;
            }

            case "Фотограф": {
                var response = new SendMessage(chatId, "Требования: подготовка репортажных фотографий для газет, журналов религиозной организации; фотографирование людей; фотографирование мероприятий; подготовка фотографий для открыток, плакатов, каталогов и т.д.; техническая настройка фотооборудования; корректировка фотографических изображений; создание необходимых визуальных эффектов; отбор фотографий для использования в газете, журнале, на Web-сайте");

                var response1 = new SendMessage(chatId, "Благодарю Вас за Ваш выбор. А теперь – три самых важных" +
                        " вопроса... по ссылке");

                var response2 = new SendMessage(chatId, "Для возвращения назад кликните ниже!");

                response1.replyMarkup(new InlineKeyboardMarkup(
                        new InlineKeyboardButton("Ссылка").url("https://docs.google.com/forms/d/e/1FAIpQLSfbj8-v" +
                                "FREt7d9V0iNgY4LSoe2YWnHFndF7GbehyNzI2mvkAw/viewform")
                ));

                response.replyMarkup(new ReplyKeyboardMarkup(
                        new KeyboardButton("Поддержка знаний")
                ));

                this.execute(response);
                this.execute(response1);
                this.execute(response2);
                break;
            }

            case "Экстрасенсорика": {
                var response = new SendMessage(chatId, "Поддержка знаний");

                response.replyMarkup(new ReplyKeyboardMarkup(
                        new String[] {"Астролог", "Гадалка", "Медиум"},
                        new String[] {"Хиромант", "Практик нетрадиционной медицины"},
                        new String[] {"Целитель", "Специалист по акупунктуре"},
                        new String[] {"Народный целитель", "Религиозное служение"}
                ));

                this.execute(response);
                break;
            }

            case "Астролог": {
                var response = new SendMessage(chatId, "Требования: консультации в сложных жизненных ситуациях; анализ проблем с учетом определенных представлений о духовном мире; предсказание судьбы; избавление от сглаза и порчи; составление гороскопов; расшифровка характеристик ладони; интерпретация вытянутых клиентом карт; интерпретация расположения чайных листьев или кофейной гущи в чашке, очертаний и рисунков костей животных и т.п.");

                var response1 = new SendMessage(chatId, "Благодарю Вас за Ваш выбор. А теперь – три самых важных" +
                        " вопроса... по ссылке");

                var response2 = new SendMessage(chatId, "Для возвращения назад кликните ниже!");

                response1.replyMarkup(new InlineKeyboardMarkup(
                        new InlineKeyboardButton("Ссылка").url("https://docs.google.com/forms/d/e/1FAIpQLSfbj8-v" +
                                "FREt7d9V0iNgY4LSoe2YWnHFndF7GbehyNzI2mvkAw/viewform")
                ));

                response.replyMarkup(new ReplyKeyboardMarkup(
                        new KeyboardButton("Экстрасенсорика")
                ));

                this.execute(response);
                this.execute(response1);
                this.execute(response2);
                break;
            }

            case "Гадалка": {
                var response = new SendMessage(chatId, "Требования: консультации в сложных жизненных ситуациях; анализ проблем с учетом определенных представлений о духовном мире; предсказание судьбы; избавление от сглаза и порчи; составление гороскопов; расшифровка характеристик ладони; интерпретация вытянутых клиентом карт; интерпретация расположения чайных листьев или кофейной гущи в чашке, очертаний и рисунков костей животных и т.п.");

                var response1 = new SendMessage(chatId, "Благодарю Вас за Ваш выбор. А теперь – три самых важных" +
                        " вопроса... по ссылке");

                var response2 = new SendMessage(chatId, "Для возвращения назад кликните ниже!");

                response1.replyMarkup(new InlineKeyboardMarkup(
                        new InlineKeyboardButton("Ссылка").url("https://docs.google.com/forms/d/e/1FAIpQLSfbj8-v" +
                                "FREt7d9V0iNgY4LSoe2YWnHFndF7GbehyNzI2mvkAw/viewform")
                ));

                response.replyMarkup(new ReplyKeyboardMarkup(
                        new KeyboardButton("Экстрасенсорика")
                ));

                this.execute(response);
                this.execute(response1);
                this.execute(response2);
                break;
            }

            case "Медиум": {
                var response = new SendMessage(chatId, "Требования: консультации в сложных жизненных ситуациях; анализ проблем с учетом определенных представлений о духовном мире; предсказание судьбы; избавление от сглаза и порчи; составление гороскопов; расшифровка характеристик ладони; интерпретация вытянутых клиентом карт; интерпретация расположения чайных листьев или кофейной гущи в чашке, очертаний и рисунков костей животных и т.п.");

                var response1 = new SendMessage(chatId, "Благодарю Вас за Ваш выбор. А теперь – три самых важных" +
                        " вопроса... по ссылке");

                var response2 = new SendMessage(chatId, "Для возвращения назад кликните ниже!");

                response1.replyMarkup(new InlineKeyboardMarkup(
                        new InlineKeyboardButton("Ссылка").url("https://docs.google.com/forms/d/e/1FAIpQLSfbj8-v" +
                                "FREt7d9V0iNgY4LSoe2YWnHFndF7GbehyNzI2mvkAw/viewform")
                ));

                response.replyMarkup(new ReplyKeyboardMarkup(
                        new KeyboardButton("Экстрасенсорика")
                ));

                this.execute(response);
                this.execute(response1);
                this.execute(response2);
                break;
            }

            case "Хиромант": {
                var response = new SendMessage(chatId, "Требования: консультации в сложных жизненных ситуациях; анализ проблем с учетом определенных представлений о духовном мире; предсказание судьбы; избавление от сглаза и порчи; составление гороскопов; расшифровка характеристик ладони; интерпретация вытянутых клиентом карт; интерпретация расположения чайных листьев или кофейной гущи в чашке, очертаний и рисунков костей животных и т.п.");

                var response1 = new SendMessage(chatId, "Благодарю Вас за Ваш выбор. А теперь – три самых важных" +
                        " вопроса... по ссылке");

                var response2 = new SendMessage(chatId, "Для возвращения назад кликните ниже!");

                response1.replyMarkup(new InlineKeyboardMarkup(
                        new InlineKeyboardButton("Ссылка").url("https://docs.google.com/forms/d/e/1FAIpQLSfbj8-v" +
                                "FREt7d9V0iNgY4LSoe2YWnHFndF7GbehyNzI2mvkAw/viewform")
                ));

                response.replyMarkup(new ReplyKeyboardMarkup(
                        new KeyboardButton("Экстрасенсорика")
                ));

                this.execute(response);
                this.execute(response1);
                this.execute(response2);
                break;
            }

            case "Практик нетрадиционной медицины": {
                var response = new SendMessage(chatId, "Требования: анализ проблем с учетом определенных представлений о духовном мире; консультирование по лечению заболевания человека через религиозные и духовные практики; рекомендации лечению болезней с использованием традиционных народных средств и методов; осуществление ухода и лечения с помощью духовных практик");

                var response1 = new SendMessage(chatId, "Благодарю Вас за Ваш выбор. А теперь – три самых важных" +
                        " вопроса... по ссылке");

                var response2 = new SendMessage(chatId, "Для возвращения назад кликните ниже!");

                response1.replyMarkup(new InlineKeyboardMarkup(
                        new InlineKeyboardButton("Ссылка").url("https://docs.google.com/forms/d/e/1FAIpQLSfbj8-v" +
                                "FREt7d9V0iNgY4LSoe2YWnHFndF7GbehyNzI2mvkAw/viewform")
                ));

                response.replyMarkup(new ReplyKeyboardMarkup(
                        new KeyboardButton("Экстрасенсорика")
                ));

                this.execute(response);
                this.execute(response1);
                this.execute(response2);
                break;
            }

            case "Целитель": {
                var response = new SendMessage(chatId, "Требования: анализ проблем с учетом определенных представлений о духовном мире; консультирование по лечению заболевания человека через религиозные и духовные практики; рекомендации лечению болезней с использованием традиционных народных средств и методов; осуществление ухода и лечения с помощью духовных практик");

                var response1 = new SendMessage(chatId, "Благодарю Вас за Ваш выбор. А теперь – три самых важных" +
                        " вопроса... по ссылке");

                var response2 = new SendMessage(chatId, "Для возвращения назад кликните ниже!");

                response1.replyMarkup(new InlineKeyboardMarkup(
                        new InlineKeyboardButton("Ссылка").url("https://docs.google.com/forms/d/e/1FAIpQLSfbj8-v" +
                                "FREt7d9V0iNgY4LSoe2YWnHFndF7GbehyNzI2mvkAw/viewform")
                ));

                response.replyMarkup(new ReplyKeyboardMarkup(
                        new KeyboardButton("Экстрасенсорика")
                ));

                this.execute(response);
                this.execute(response1);
                this.execute(response2);
                break;
            }

            case "Специалист по акупунктуре": {
                var response = new SendMessage(chatId, "Требования: анализ проблем с учетом определенных представлений о духовном мире; консультирование по лечению заболевания человека через религиозные и духовные практики; рекомендации лечению болезней с использованием традиционных народных средств и методов; осуществление ухода и лечения с помощью духовных практик");

                var response1 = new SendMessage(chatId, "Благодарю Вас за Ваш выбор. А теперь – три самых важных" +
                        " вопроса... по ссылке");

                var response2 = new SendMessage(chatId, "Для возвращения назад кликните ниже!");

                response1.replyMarkup(new InlineKeyboardMarkup(
                        new InlineKeyboardButton("Ссылка").url("https://docs.google.com/forms/d/e/1FAIpQLSfbj8-v" +
                                "FREt7d9V0iNgY4LSoe2YWnHFndF7GbehyNzI2mvkAw/viewform")
                ));

                response.replyMarkup(new ReplyKeyboardMarkup(
                        new KeyboardButton("Экстрасенсорика")
                ));

                this.execute(response);
                this.execute(response1);
                this.execute(response2);
                break;
            }

            case "Народный целитель": {
                var response = new SendMessage(chatId, "Требования: анализ проблем с учетом определенных представлений о духовном мире; консультирование по лечению заболевания человека через религиозные и духовные практики; рекомендации лечению болезней с использованием традиционных народных средств и методов; осуществление ухода и лечения с помощью духовных практик");

                var response1 = new SendMessage(chatId, "Благодарю Вас за Ваш выбор. А теперь – три самых важных" +
                        " вопроса... по ссылке");

                var response2 = new SendMessage(chatId, "Для возвращения назад кликните ниже!");

                response1.replyMarkup(new InlineKeyboardMarkup(
                        new InlineKeyboardButton("Ссылка").url("https://docs.google.com/forms/d/e/1FAIpQLSfbj8-v" +
                                "FREt7d9V0iNgY4LSoe2YWnHFndF7GbehyNzI2mvkAw/viewform")
                ));

                response.replyMarkup(new ReplyKeyboardMarkup(
                        new KeyboardButton("Экстрасенсорика")
                ));

                this.execute(response);
                this.execute(response1);
                this.execute(response2);
                break;
            }

            case "Организационная поддержка": {
                var response = new SendMessage(chatId, "Организационная поддержка");

                response.replyMarkup(new ReplyKeyboardMarkup(
                        new String[] {"Бальзамировщик, Гробовщик, Организатор ритуала"},
                        new String[] {"Директор службы по уходу за престарелыми, Директор частной клиники по уходу"},
                        new String[] {"Специалист по социальной работе с уязвимыми слоями населения (безработные, малообеспеченные, инвалиды и др.), Специалист по социальной работе, уход за престарелыми и инвалидами, Социальный работник"},
                        new String[] {"Медицинская(ий) сестра/брат"},
                        new String[] {"Помощник по уходу (в больнице или клинике), Помощник по уходу за больными, Помощник по уходу на дому, Сиделка"},
                        new String[] {"Руководитель отдела по связям с общественностью"},
                        new String[] {"Специалист по связям с общественностью, Пресс-секретарь"},
                        new String[] {"Юрисконсульт", "Психолог"},
                        new String[] {"Специалист по кадровым вопросам"},
                        new String[] {"Религиозное служение"}
                ));

                this.execute(response);
                break;
            }

            case "Бальзамировщик, Гробовщик, Организатор ритуала": {
                var response = new SendMessage(chatId, "Требования: организация религиозных погребальных обрядов; проведение похорон в соответствии с религиозными представлениями о загробной жизни; помощь при выборе гробов или погребальных урн; бальзамирование останков человека");

                var response1 = new SendMessage(chatId, "Благодарю Вас за Ваш выбор. А теперь – три самых важных" +
                        " вопроса... по ссылке");

                var response2 = new SendMessage(chatId, "Для возвращения назад кликните ниже!");

                response1.replyMarkup(new InlineKeyboardMarkup(
                        new InlineKeyboardButton("Ссылка").url("https://docs.google.com/forms/d/e/1FAIpQLSfbj8-v" +
                                "FREt7d9V0iNgY4LSoe2YWnHFndF7GbehyNzI2mvkAw/viewform")
                ));

                response.replyMarkup(new ReplyKeyboardMarkup(
                        new KeyboardButton("Организационная поддержка")
                ));

                this.execute(response);
                this.execute(response1);
                this.execute(response2);
                break;
            }

            case "Директор службы по уходу за престарелыми, Директор частной клиники по уходу": {
                var response = new SendMessage(chatId, "Требования: общее руководство службой по уходу за престарелыми, приютом, богадельней, созданной религиозной организацией; контроль работы врачей, медсестер, сестер милосердия и другого персонала; решение о приеме пациентов; подбор и обучение персонала; контроль качества ухода за больными и престарелыми; планирование бюджета и учет расходов; подготовка общих отчетов о работе; поддержание связей с другими медицинскими учреждениями социальными службами");

                var response1 = new SendMessage(chatId, "Благодарю Вас за Ваш выбор. А теперь – три самых важных" +
                        " вопроса... по ссылке");

                var response2 = new SendMessage(chatId, "Для возвращения назад кликните ниже!");

                response1.replyMarkup(new InlineKeyboardMarkup(
                        new InlineKeyboardButton("Ссылка").url("https://docs.google.com/forms/d/e/1FAIpQLSfbj8-v" +
                                "FREt7d9V0iNgY4LSoe2YWnHFndF7GbehyNzI2mvkAw/viewform")
                ));

                response.replyMarkup(new ReplyKeyboardMarkup(
                        new KeyboardButton("Организационная поддержка")
                ));

                this.execute(response);
                this.execute(response1);
                this.execute(response2);
                break;
            }

            case "Специалист по социальной работе с уязвимыми слоями населения (безработные, малообеспеченные, инвалиды и др.), Специалист по социальной работе, уход за престарелыми и инвалидами, Социальный работник": {
                var response = new SendMessage(chatId, "Требования: прием и анализ просьб о помощи; планирование помощи с учетом содержания определенного вероучения и норм религиозной морали; поддержание контактов с другими социальными службами; организация помощи в уходе и лечении; организация юридической помощи; предоставление помощи в критических ситуациях; сбор средств; помощь в приобретении профессии и трудоустройстве");

                var response1 = new SendMessage(chatId, "Благодарю Вас за Ваш выбор. А теперь – три самых важных" +
                        " вопроса... по ссылке");

                var response2 = new SendMessage(chatId, "Для возвращения назад кликните ниже!");

                response1.replyMarkup(new InlineKeyboardMarkup(
                        new InlineKeyboardButton("Ссылка").url("https://docs.google.com/forms/d/e/1FAIpQLSfbj8-v" +
                                "FREt7d9V0iNgY4LSoe2YWnHFndF7GbehyNzI2mvkAw/viewform")
                ));

                response.replyMarkup(new ReplyKeyboardMarkup(
                        new KeyboardButton("Организационная поддержка")
                ));

                this.execute(response);
                this.execute(response1);
                this.execute(response2);
                break;
            }

            case "Медицинская(ий) сестра/брат": {
                var response = new SendMessage(chatId, "Требования: медицинские консультации и проведение лечения пациентов, назначенного врачами; обеспечение ухода и личной гигиены пациентов; введение лекарств; наблюдение за состоянием пациентов; направление пациентов к врачу для оказания специализированной помощи по мере необходимости; очищение ран и перевязки; сбор и обновление информации о пациентах; оказание помощи при планировании и организации ухода за каждым пациентом");

                var response1 = new SendMessage(chatId, "Благодарю Вас за Ваш выбор. А теперь – три самых важных" +
                        " вопроса... по ссылке");

                var response2 = new SendMessage(chatId, "Для возвращения назад кликните ниже!");

                response1.replyMarkup(new InlineKeyboardMarkup(
                        new InlineKeyboardButton("Ссылка").url("https://docs.google.com/forms/d/e/1FAIpQLSfbj8-v" +
                                "FREt7d9V0iNgY4LSoe2YWnHFndF7GbehyNzI2mvkAw/viewform")
                ));

                response.replyMarkup(new ReplyKeyboardMarkup(
                        new KeyboardButton("Организационная поддержка")
                ));

                this.execute(response);
                this.execute(response1);
                this.execute(response2);
                break;
            }

            case "Помощник по уходу (в больнице или клинике), Помощник по уходу за больными, Помощник по уходу на дому, Сиделка": {
                var response = new SendMessage(chatId, "Требования: помощь в уходе за больными в медицинских учреждениях; обеспечение лечения пациентов медицинских учреждениях, созданных религиозными организациями; контроль изменений состояния пациента; оказание пациентам помощи при приеме лекарств, смене повязок и т.д.; уборка, смена постельного белья, стирка одежды и мытье посуды пациентов; проведение массажа и других процедур; помощь в соблюдении личной гигиены, кормлении, одевании, приеме лекарств и т.д.; сопровождение пациентов во время визитов к врачам");

                var response1 = new SendMessage(chatId, "Благодарю Вас за Ваш выбор. А теперь – три самых важных" +
                        " вопроса... по ссылке");

                var response2 = new SendMessage(chatId, "Для возвращения назад кликните ниже!");

                response1.replyMarkup(new InlineKeyboardMarkup(
                        new InlineKeyboardButton("Ссылка").url("https://docs.google.com/forms/d/e/1FAIpQLSfbj8-v" +
                                "FREt7d9V0iNgY4LSoe2YWnHFndF7GbehyNzI2mvkAw/viewform")
                ));

                response.replyMarkup(new ReplyKeyboardMarkup(
                        new KeyboardButton("Организационная поддержка")
                ));

                this.execute(response);
                this.execute(response1);
                this.execute(response2);
                break;
            }

            case "Руководитель отдела по связям с общественностью": {
                var response = new SendMessage(chatId, "Требования: планирование коммуникационной стратегии религиозной организации; консультирование главы религиозной общины по вопросам общения со СМИ, публичных выступлений и т.д.; планирование мероприятий; планирование работы со СМИ; информирование СМИ и общественности о планах и позиции религиозной организации; представление интересов организаций в средствах массовой информации; управление персоналом, занятым связями с общественностью; управление бюджетами, контроль расходов");

                var response1 = new SendMessage(chatId, "Благодарю Вас за Ваш выбор. А теперь – три самых важных" +
                        " вопроса... по ссылке");

                var response2 = new SendMessage(chatId, "Для возвращения назад кликните ниже!");

                response1.replyMarkup(new InlineKeyboardMarkup(
                        new InlineKeyboardButton("Ссылка").url("https://docs.google.com/forms/d/e/1FAIpQLSfbj8-v" +
                                "FREt7d9V0iNgY4LSoe2YWnHFndF7GbehyNzI2mvkAw/viewform")
                ));

                response.replyMarkup(new ReplyKeyboardMarkup(
                        new KeyboardButton("Организационная поддержка")
                ));

                this.execute(response);
                this.execute(response1);
                this.execute(response2);
                break;
            }

            case "Специалист по связям с общественностью, Пресс-секретарь": {
                var response = new SendMessage(chatId, "Требования: планирование коммуникационной стратегии религиозной организации; изучение общественного мнения по религиозным вопросам; информирование СМИ и общественности о планах и точке зрения религиозной организации; выпуск новостей и пресс-релизов; подготовка и проведение специальных мероприятий; представление интересов организаций в средствах массовой информации; организация интервью с руководителем; организация интервью с руководителем");

                var response1 = new SendMessage(chatId, "Благодарю Вас за Ваш выбор. А теперь – три самых важных" +
                        " вопроса... по ссылке");

                var response2 = new SendMessage(chatId, "Для возвращения назад кликните ниже!");

                response1.replyMarkup(new InlineKeyboardMarkup(
                        new InlineKeyboardButton("Ссылка").url("https://docs.google.com/forms/d/e/1FAIpQLSfbj8-v" +
                                "FREt7d9V0iNgY4LSoe2YWnHFndF7GbehyNzI2mvkAw/viewform")
                ));

                response.replyMarkup(new ReplyKeyboardMarkup(
                        new KeyboardButton("Организационная поддержка")
                ));

                this.execute(response);
                this.execute(response1);
                this.execute(response2);
                break;
            }

            case "Юрисконсульт": {
                var response = new SendMessage(chatId, "Требования: представление правовых интересов религиозной организации; консультации и представительство по гражданским делам; консультации и представительство по уголовным делам; консультации и представительство по трудовым споров; осуществление защиты; ведение переговоров о достижении соглашения; консультации по составлению юридических документов; составление юридических документов");

                var response1 = new SendMessage(chatId, "Благодарю Вас за Ваш выбор. А теперь – три самых важных" +
                        " вопроса... по ссылке");

                var response2 = new SendMessage(chatId, "Для возвращения назад кликните ниже!");

                response1.replyMarkup(new InlineKeyboardMarkup(
                        new InlineKeyboardButton("Ссылка").url("https://docs.google.com/forms/d/e/1FAIpQLSfbj8-v" +
                                "FREt7d9V0iNgY4LSoe2YWnHFndF7GbehyNzI2mvkAw/viewform")
                ));

                response.replyMarkup(new ReplyKeyboardMarkup(
                        new KeyboardButton("Организационная поддержка")
                ));

                this.execute(response);
                this.execute(response1);
                this.execute(response2);
                break;
            }

            case "Психолог": {
                var response = new SendMessage(chatId, "Требования: исследование религиозного сознания; проведение тестов; анализ влияния религиозности на мышление и поведение личности; разработка теорий, моделей и методов для объяснения и описания поведения человека; разработка тестов для использования преподавателями; подготовка научных статей; консультирование на основе психологических исследований и религиозных представлений о душе человека; рекомендации касательно возможных способов решения проблем с точки зрения религиозной психологии");

                var response1 = new SendMessage(chatId, "Благодарю Вас за Ваш выбор. А теперь – три самых важных" +
                        " вопроса... по ссылке");

                var response2 = new SendMessage(chatId, "Для возвращения назад кликните ниже!");

                response1.replyMarkup(new InlineKeyboardMarkup(
                        new InlineKeyboardButton("Ссылка").url("https://docs.google.com/forms/d/e/1FAIpQLSfbj8-v" +
                                "FREt7d9V0iNgY4LSoe2YWnHFndF7GbehyNzI2mvkAw/viewform")
                ));

                response.replyMarkup(new ReplyKeyboardMarkup(
                        new KeyboardButton("Организационная поддержка")
                ));

                this.execute(response);
                this.execute(response1);
                this.execute(response2);
                break;
            }

            case "Специалист по кадровым вопросам": {
                var response = new SendMessage(chatId, "Требования: составление списков вакантных должностей; определение потребности в работниках; поиск и отбор кандидатов с соответствующей квалификацией и личными качествами; найм работников; ведение кадровой отчетности; координация отношений руководителей и работников; организация повышения квалификации работников; консультирование работников по вопросам заработной платы, отпуска, возможностей роста");

                var response1 = new SendMessage(chatId, "Благодарю Вас за Ваш выбор. А теперь – три самых важных" +
                        " вопроса... по ссылке");

                var response2 = new SendMessage(chatId, "Для возвращения назад кликните ниже!");

                response1.replyMarkup(new InlineKeyboardMarkup(
                        new InlineKeyboardButton("Ссылка").url("https://docs.google.com/forms/d/e/1FAIpQLSfbj8-v" +
                                "FREt7d9V0iNgY4LSoe2YWnHFndF7GbehyNzI2mvkAw/viewform")
                ));

                response.replyMarkup(new ReplyKeyboardMarkup(
                        new KeyboardButton("Организационная поддержка")
                ));

                this.execute(response);
                this.execute(response1);
                this.execute(response2);
                break;
            }

            case "Помощь по хозяйству": {
                var response = new SendMessage(chatId, "Помощь по хозяйству");

                response.replyMarkup(new ReplyKeyboardMarkup(
                        new String[] {"Главный бухгалтер", "Бухгалтер"},
                        new String[] {"Руководитель делопроизводства , Руководитель канцелярии"},
                        new String[] {"Служащий канцелярии", "Секретарь-референт"},
                        new String[] {"Гид (музей, художественная галерея, путешествие), Экскурсовод"},
                        new String[] {"Консультант-продавец", "Сторож, Охранник, Вахтёр"},
                        new String[] {"Уборщик, уборщица", "Дворник"},
                        new String[] {"Религиозное служение"}
                ));

                this.execute(response);
                break;
            }

            case "Главный бухгалтер": {
                var response = new SendMessage(chatId, "Требования: ведение бухгалтерского учета религиозной организации; планирование бюджета и финансовой деятельности; оценка финансового положения; контроль над финансовыми операциями; контроль подбора персонала; финансовое представительство в отношениях с внешними учреждениями; руководство повседневной финансовой деятельностью; консультации с руководителями");

                var response1 = new SendMessage(chatId, "Благодарю Вас за Ваш выбор. А теперь – три самых важных" +
                        " вопроса... по ссылке");

                var response2 = new SendMessage(chatId, "Для возвращения назад кликните ниже!");

                response1.replyMarkup(new InlineKeyboardMarkup(
                        new InlineKeyboardButton("Ссылка").url("https://docs.google.com/forms/d/e/1FAIpQLSfbj8-v" +
                                "FREt7d9V0iNgY4LSoe2YWnHFndF7GbehyNzI2mvkAw/viewform")
                ));

                response.replyMarkup(new ReplyKeyboardMarkup(
                        new KeyboardButton("Помощь по хозяйству")
                ));

                this.execute(response);
                this.execute(response1);
                this.execute(response2);
                break;
            }

            case "Бухгалтер": {
                var response = new SendMessage(chatId, "Требования: ведение бухгалтерского учета религиозной организации; подготовка финансовых отчетов; подготовка отчетов по прогнозированию доходов; подготовка бюджета; оформление сделок; проведение финансовых операций; проверка отчетности и бухгалтерских документов; консультации с руководителями");

                var response1 = new SendMessage(chatId, "Благодарю Вас за Ваш выбор. А теперь – три самых важных" +
                        " вопроса... по ссылке");

                var response2 = new SendMessage(chatId, "Для возвращения назад кликните ниже!");

                response1.replyMarkup(new InlineKeyboardMarkup(
                        new InlineKeyboardButton("Ссылка").url("https://docs.google.com/forms/d/e/1FAIpQLSfbj8-v" +
                                "FREt7d9V0iNgY4LSoe2YWnHFndF7GbehyNzI2mvkAw/viewform")
                ));

                response.replyMarkup(new ReplyKeyboardMarkup(
                        new KeyboardButton("Помощь по хозяйству")
                ));

                this.execute(response);
                this.execute(response1);
                this.execute(response2);
                break;
            }

            case "Руководитель делопроизводства , Руководитель канцелярии": {
                var response = new SendMessage(chatId, "Требования: руководство работниками канцелярии; координация работы канцелярии; подготовка планов и отчетов; подготовка документов; регистрация и хранение документов; обработка корреспонденции; подготовка писем, резюме; выполнение обязанностей, требующих общих навыков канцелярской и административной работы.");

                var response1 = new SendMessage(chatId, "Благодарю Вас за Ваш выбор. А теперь – три самых важных" +
                        " вопроса... по ссылке");

                var response2 = new SendMessage(chatId, "Для возвращения назад кликните ниже!");

                response1.replyMarkup(new InlineKeyboardMarkup(
                        new InlineKeyboardButton("Ссылка").url("https://docs.google.com/forms/d/e/1FAIpQLSfbj8-v" +
                                "FREt7d9V0iNgY4LSoe2YWnHFndF7GbehyNzI2mvkAw/viewform")
                ));

                response.replyMarkup(new ReplyKeyboardMarkup(
                        new KeyboardButton("Помощь по хозяйству")
                ));

                this.execute(response);
                this.execute(response1);
                this.execute(response2);
                break;
            }

            case "Служащий канцелярии": {
                var response = new SendMessage(chatId, "Требования: подготовка документов; редактирование документов; прием посетителей; ответы на телефонные звонки или электронные письма; ввод данных, их распечатка; копирование и размножение документов; составление отчетов; написание писем и резюме.");

                var response1 = new SendMessage(chatId, "Благодарю Вас за Ваш выбор. А теперь – три самых важных" +
                        " вопроса... по ссылке");

                var response2 = new SendMessage(chatId, "Для возвращения назад кликните ниже!");

                response1.replyMarkup(new InlineKeyboardMarkup(
                        new InlineKeyboardButton("Ссылка").url("https://docs.google.com/forms/d/e/1FAIpQLSfbj8-v" +
                                "FREt7d9V0iNgY4LSoe2YWnHFndF7GbehyNzI2mvkAw/viewform")
                ));

                response.replyMarkup(new ReplyKeyboardMarkup(
                        new KeyboardButton("Помощь по хозяйству")
                ));

                this.execute(response);
                this.execute(response1);
                this.execute(response2);
                break;
            }

            case "Секретарь-референт": {
                var response = new SendMessage(chatId, "Требования: набор и форматирование текстов; прием посетителей; ответы на телефонные звонки и письма; работа с входящей или исходящей почтой; составление графиков встреч и мероприятий руководителя; помощь в организации встреч и мероприятий; проверка, форматирование и печатание документации, писем, протоколов и отчетов; организация регистрации и хранения документов.");

                var response1 = new SendMessage(chatId, "Благодарю Вас за Ваш выбор. А теперь – три самых важных" +
                        " вопроса... по ссылке");

                var response2 = new SendMessage(chatId, "Для возвращения назад кликните ниже!");

                response1.replyMarkup(new InlineKeyboardMarkup(
                        new InlineKeyboardButton("Ссылка").url("https://docs.google.com/forms/d/e/1FAIpQLSfbj8-v" +
                                "FREt7d9V0iNgY4LSoe2YWnHFndF7GbehyNzI2mvkAw/viewform")
                ));

                response.replyMarkup(new ReplyKeyboardMarkup(
                        new KeyboardButton("Помощь по хозяйству")
                ));

                this.execute(response);
                this.execute(response1);
                this.execute(response2);
                break;
            }

            case "Гид (музей, художественная галерея, путешествие), Экскурсовод": {
                var response = new SendMessage(chatId, "Требования: организация туров (паломничеств); проведение экскурсий для детей; сопровождение туристов и паломников при посещении достопримечательностей, имеющих религиозную ценность; рассказ о достопримечательностях и экспонатах; контроль соблюдения правил поведения в посещаемых местах; обеспечение физической безопасности туристов и паломников; ответы на вопросы туристов и паломников; решение разнообразных проблем туристов и паломников.");

                var response1 = new SendMessage(chatId, "Благодарю Вас за Ваш выбор. А теперь – три самых важных" +
                        " вопроса... по ссылке");

                var response2 = new SendMessage(chatId, "Для возвращения назад кликните ниже!");

                response1.replyMarkup(new InlineKeyboardMarkup(
                        new InlineKeyboardButton("Ссылка").url("https://docs.google.com/forms/d/e/1FAIpQLSfbj8-v" +
                                "FREt7d9V0iNgY4LSoe2YWnHFndF7GbehyNzI2mvkAw/viewform")
                ));

                response.replyMarkup(new ReplyKeyboardMarkup(
                        new KeyboardButton("Помощь по хозяйству")
                ));

                this.execute(response);
                this.execute(response1);
                this.execute(response2);
                break;
            }

            case "Консультант-продавец": {
                var response = new SendMessage(chatId, "Требования: торговля сувенирами и предметами религиозного назначения; торговля книгами религиозного содержания; торговля записями бесед, лекций, проповедей и религиозными видеофильмами; ответы на вопросы об использовании товаров; ответы на вопросы об услугах, оказываемых религиозной организацией; получение оплаты, выдача чеков, счетов-фактур и т.д; проведение инвентаризации и прием заказов на товары; укладка и размещение товаров для продажи.");

                var response1 = new SendMessage(chatId, "Благодарю Вас за Ваш выбор. А теперь – три самых важных" +
                        " вопроса... по ссылке");

                var response2 = new SendMessage(chatId, "Для возвращения назад кликните ниже!");

                response1.replyMarkup(new InlineKeyboardMarkup(
                        new InlineKeyboardButton("Ссылка").url("https://docs.google.com/forms/d/e/1FAIpQLSfbj8-v" +
                                "FREt7d9V0iNgY4LSoe2YWnHFndF7GbehyNzI2mvkAw/viewform")
                ));

                response.replyMarkup(new ReplyKeyboardMarkup(
                        new KeyboardButton("Помощь по хозяйству")
                ));

                this.execute(response);
                this.execute(response1);
                this.execute(response2);
                break;
            }

            case "Сторож, Охранник, Вахтёр": {
                var response = new SendMessage(chatId, "Требования: наружный и внутренний обход охраняемых религиозных сооружений; охрана помещений и территории; мониторинг систем охранной и пожарной сигнализации; охрана общественного порядка; охрана собственности храма и посетителей от кражи; контроль входа в помещение; контроль над соблюдением правил поведения в храме; реагирование на сигналы тревоги.");

                var response1 = new SendMessage(chatId, "Благодарю Вас за Ваш выбор. А теперь – три самых важных" +
                        " вопроса... по ссылке");

                var response2 = new SendMessage(chatId, "Для возвращения назад кликните ниже!");

                response1.replyMarkup(new InlineKeyboardMarkup(
                        new InlineKeyboardButton("Ссылка").url("https://docs.google.com/forms/d/e/1FAIpQLSfbj8-v" +
                                "FREt7d9V0iNgY4LSoe2YWnHFndF7GbehyNzI2mvkAw/viewform")
                ));

                response.replyMarkup(new ReplyKeyboardMarkup(
                        new KeyboardButton("Помощь по хозяйству")
                ));

                this.execute(response);
                this.execute(response1);
                this.execute(response2);
                break;
            }

            case "Уборщик, уборщица": {
                var response = new SendMessage(chatId, "Требования: уборка внутренних помещений храма; мойка окон, проходов и т.д.; поддержание порядка и чистоты; сбор мусора; мойка полов, мебели; мойка, чистка подсвечников и другой храмовой утвари; мойка и чистка ковров; содержание в чистоте кладовых, подсобных помещений.");

                var response1 = new SendMessage(chatId, "Благодарю Вас за Ваш выбор. А теперь – три самых важных" +
                        " вопроса... по ссылке");

                var response2 = new SendMessage(chatId, "Для возвращения назад кликните ниже!");

                response1.replyMarkup(new InlineKeyboardMarkup(
                        new InlineKeyboardButton("Ссылка").url("https://docs.google.com/forms/d/e/1FAIpQLSfbj8-v" +
                                "FREt7d9V0iNgY4LSoe2YWnHFndF7GbehyNzI2mvkAw/viewform")
                ));

                response.replyMarkup(new ReplyKeyboardMarkup(
                        new KeyboardButton("Помощь по хозяйству")
                ));

                this.execute(response);
                this.execute(response1);
                this.execute(response2);
                break;
            }

            case "Дворник": {
                var response = new SendMessage(chatId, "Требования: сбор мусора в контейнеры, баки и т.д.; очистка урн; промывка и дезинфекция урн; сбор строительного мусора; уборка территории от пыли, увлажнение; уборка территории от снега и льда; очистка крышек канализационных, газовых и пожарных колодцев; полив зеленых насаждений.");

                var response1 = new SendMessage(chatId, "Благодарю Вас за Ваш выбор. А теперь – три самых важных" +
                        " вопроса... по ссылке");

                var response2 = new SendMessage(chatId, "Для возвращения назад кликните ниже!");

                response1.replyMarkup(new InlineKeyboardMarkup(
                        new InlineKeyboardButton("Ссылка").url("https://docs.google.com/forms/d/e/1FAIpQLSfbj8-v" +
                                "FREt7d9V0iNgY4LSoe2YWnHFndF7GbehyNzI2mvkAw/viewform")
                ));

                response.replyMarkup(new ReplyKeyboardMarkup(
                        new KeyboardButton("Помощь по хозяйству")
                ));

                this.execute(response);
                this.execute(response1);
                this.execute(response2);
                break;
            }


            case "Дипломатическая служба": {
                var response = new SendMessage(chatId, "Дипломатическая служба");

                response.replyMarkup(new ReplyKeyboardMarkup(
                        new String[] {"Kоммуникация", "Организационная поддержка."},
                        new String[] {"Служение"}
                ));

                this.execute(response);
                break;
            }

            case "Kоммуникация": {
                var response = new SendMessage(chatId, "Коммуникация:");

                response.replyMarkup(new ReplyKeyboardMarkup(
                        new String[] {"Посол", "Атташе"},
                        new String[] {"Дипломатическая служба"}
                ));

                this.execute(response);
                break;
            }

            case "Посол": {
                var response = new SendMessage(chatId, "Требования: 1. Реализация политики представляемого государства 2. Посредничество в принятии решений на международном уровне 3. Участие в совещаниях и форумах; 4. Планирование работы дипломатических ведомств; - Взаимодействие с политиками и общественными деятелями страны пребывания; - Организация контактов между представителями своей страны и страны пребывания; - Выполнение поручений вышестоящих руководителей; - Взаимодействие с местными СМИ");

                var response1 = new SendMessage(chatId, "Благодарю Вас за Ваш выбор. А теперь – три самых важных" +
                        " вопроса... по ссылке");

                var response2 = new SendMessage(chatId, "Для возвращения назад кликните ниже!");

                response1.replyMarkup(new InlineKeyboardMarkup(
                        new InlineKeyboardButton("Ссылка").url("https://docs.google.com/forms/d/e/1FAIpQLSfbj8-v" +
                                "FREt7d9V0iNgY4LSoe2YWnHFndF7GbehyNzI2mvkAw/viewform")
                ));

                response.replyMarkup(new ReplyKeyboardMarkup(
                        new KeyboardButton("Kоммуникация")
                ));

                this.execute(response);
                this.execute(response1);
                this.execute(response2);
                break;
            }

            case "Атташе": {
                var response = new SendMessage(chatId, "Требования: 1. Реализация политики представляемого государства 2. Посредничество в принятии решений на международном уровне 3. Участие в совещаниях и форумах; 4. Планирование работы дипломатических ведомств; - Взаимодействие с политиками и общественными деятелями страны пребывания; - Организация контактов между представителями своей страны и страны пребывания; - Выполнение поручений вышестоящих руководителей; - Взаимодействие с местными СМИ");

                var response1 = new SendMessage(chatId, "Благодарю Вас за Ваш выбор. А теперь – три самых важных" +
                        " вопроса... по ссылке");

                var response2 = new SendMessage(chatId, "Для возвращения назад кликните ниже!");

                response1.replyMarkup(new InlineKeyboardMarkup(
                        new InlineKeyboardButton("Ссылка").url("https://docs.google.com/forms/d/e/1FAIpQLSfbj8-v" +
                                "FREt7d9V0iNgY4LSoe2YWnHFndF7GbehyNzI2mvkAw/viewform")
                ));

                response.replyMarkup(new ReplyKeyboardMarkup(
                        new KeyboardButton("Kоммуникация")
                ));

                this.execute(response);
                this.execute(response1);
                this.execute(response2);
                break;
            }

            case "Организационная поддержка.": {
                var response = new SendMessage(chatId, "Организационная поддержка:");

                response.replyMarkup(new ReplyKeyboardMarkup(
                        new String[] {"Руководитель отдела, Директор, Советник"},
                        new String[] {"Секретарь, Делопроизводитель, Офисный работник"},
                        new String[] {"Аудитор, Бухгалтер, Главный бухгалтер"},
                        new String[] {"Специалист по подбору персонала"},
                        new String[] {"Судья", "Юрист, Нотариус"},
                        new String[] {"Дипломатическая служба"}
                ));

                this.execute(response);
                break;
            }

            case "Руководитель отдела, Директор, Советник": {
                var response = new SendMessage(chatId, "Требования: 1. Организация работы международной организации 2. Анализ работы организации 3. Подбор новых руководителей и сотрудников 4. Контроль за соблюдением законов организацией; - Контроль качества работы сотрудников; - Взаимодействие с вышестоящими руководителями по вопросам работы организации; - Переговоры с клиентами и партнёрами; - Контроль финансового благополучия организации");

                var response1 = new SendMessage(chatId, "Благодарю Вас за Ваш выбор. А теперь – три самых важных" +
                        " вопроса... по ссылке");

                var response2 = new SendMessage(chatId, "Для возвращения назад кликните ниже!");

                response1.replyMarkup(new InlineKeyboardMarkup(
                        new InlineKeyboardButton("Ссылка").url("https://docs.google.com/forms/d/e/1FAIpQLSfbj8-v" +
                                "FREt7d9V0iNgY4LSoe2YWnHFndF7GbehyNzI2mvkAw/viewform")
                ));

                response.replyMarkup(new ReplyKeyboardMarkup(
                        new KeyboardButton("Организационная поддержка.")
                ));

                this.execute(response);
                this.execute(response1);
                this.execute(response2);
                break;
            }

            case "Секретарь, Делопроизводитель, Офисный работник": {
                var response = new SendMessage(chatId, "Требования: 1. Обработка писем, поступающих в дипломатические ведомства 2. Регистрация, учёт и хранение документов 3. Ответы на звонки и письменные запросы 4. Контроль оформления документов 4. Контроль сроков исполнения документов 1. Составление текстов документов 2. Распечатка, копирование и сканирование документов 3. Помощь вышестоящим руководителям");

                var response1 = new SendMessage(chatId, "Благодарю Вас за Ваш выбор. А теперь – три самых важных" +
                        " вопроса... по ссылке");

                var response2 = new SendMessage(chatId, "Для возвращения назад кликните ниже!");

                response1.replyMarkup(new InlineKeyboardMarkup(
                        new InlineKeyboardButton("Ссылка").url("https://docs.google.com/forms/d/e/1FAIpQLSfbj8-v" +
                                "FREt7d9V0iNgY4LSoe2YWnHFndF7GbehyNzI2mvkAw/viewform")
                ));

                response.replyMarkup(new ReplyKeyboardMarkup(
                        new KeyboardButton("Организационная поддержка.")
                ));

                this.execute(response);
                this.execute(response1);
                this.execute(response2);
                break;
            }

            case "Аудитор, Бухгалтер, Главный бухгалтер": {
                var response = new SendMessage(chatId, "Требования: 1. Управление финансами в дипломатических ведомствах 2. Контроль за финансовыми операциями 3. Консультации с руководителями 4. Разработка бюджета - Приём и обработка финансовых документов; - Ведение налогового учёта; - Взаимодействие с банками; - Оценка экономической эффективности");

                var response1 = new SendMessage(chatId, "Благодарю Вас за Ваш выбор. А теперь – три самых важных" +
                        " вопроса... по ссылке");

                var response2 = new SendMessage(chatId, "Для возвращения назад кликните ниже!");

                response1.replyMarkup(new InlineKeyboardMarkup(
                        new InlineKeyboardButton("Ссылка").url("https://docs.google.com/forms/d/e/1FAIpQLSfbj8-v" +
                                "FREt7d9V0iNgY4LSoe2YWnHFndF7GbehyNzI2mvkAw/viewform")
                ));

                response.replyMarkup(new ReplyKeyboardMarkup(
                        new KeyboardButton("Организационная поддержка.")
                ));

                this.execute(response);
                this.execute(response1);
                this.execute(response2);
                break;
            }

            case "Специалист по подбору персонала": {
                var response = new SendMessage(chatId, "Требования: 1. Мониторинг ситуации с персоналом в дипломатических ведомствах; 2. Найм новых работников; 2. Решение вопросов работников; 3. Обучение сотрудников; 4. Контроль качества обучения персонала; - Участие в контроле эффективности работы персонала; - Участие в выстраивании системы зарплат и поощрений; - Поддержка корпоративной культуры в организации");

                var response1 = new SendMessage(chatId, "Благодарю Вас за Ваш выбор. А теперь – три самых важных" +
                        " вопроса... по ссылке");

                var response2 = new SendMessage(chatId, "Для возвращения назад кликните ниже!");

                response1.replyMarkup(new InlineKeyboardMarkup(
                        new InlineKeyboardButton("Ссылка").url("https://docs.google.com/forms/d/e/1FAIpQLSfbj8-v" +
                                "FREt7d9V0iNgY4LSoe2YWnHFndF7GbehyNzI2mvkAw/viewform")
                ));

                response.replyMarkup(new ReplyKeyboardMarkup(
                        new KeyboardButton("Организационная поддержка.")
                ));

                this.execute(response);
                this.execute(response1);
                this.execute(response2);
                break;
            }

            case "Судья": {
                var response = new SendMessage(chatId, "Требования: 1. Проведение судебных заседаний в международных судах; 1. Вынесение решений; 3. Участие в расследовании правонарушений; - Формирование состава судейских органов; - Рассмотрение решений, вынесенных другими судами; - Пересмотр собственных вынесенных решений; - Взаимодействие с государственными органами; - Взаимодействие с вышестоящими руководителями");

                var response1 = new SendMessage(chatId, "Благодарю Вас за Ваш выбор. А теперь – три самых важных" +
                        " вопроса... по ссылке");

                var response2 = new SendMessage(chatId, "Для возвращения назад кликните ниже!");

                response1.replyMarkup(new InlineKeyboardMarkup(
                        new InlineKeyboardButton("Ссылка").url("https://docs.google.com/forms/d/e/1FAIpQLSfbj8-v" +
                                "FREt7d9V0iNgY4LSoe2YWnHFndF7GbehyNzI2mvkAw/viewform")
                ));

                response.replyMarkup(new ReplyKeyboardMarkup(
                        new KeyboardButton("Организационная поддержка.")
                ));

                this.execute(response);
                this.execute(response1);
                this.execute(response2);
                break;
            }

            case "Юрист, Нотариус": {
                var response = new SendMessage(chatId, "Требования: 2. Консультирование по международному праву; 4. Заверение документов; 1. Подготовка юридических актов по вопросам с международной спецификой; 2. Оценка договоров между организациями; - Участие в судебных заседаниях; - Разрешение споров; - Проведение исследований в области права; - Проверка подлинности перевода правового акта с одного языка на другой");

                var response1 = new SendMessage(chatId, "Благодарю Вас за Ваш выбор. А теперь – три самых важных" +
                        " вопроса... по ссылке");

                var response2 = new SendMessage(chatId, "Для возвращения назад кликните ниже!");

                response1.replyMarkup(new InlineKeyboardMarkup(
                        new InlineKeyboardButton("Ссылка").url("https://docs.google.com/forms/d/e/1FAIpQLSfbj8-v" +
                                "FREt7d9V0iNgY4LSoe2YWnHFndF7GbehyNzI2mvkAw/viewform")
                ));

                response.replyMarkup(new ReplyKeyboardMarkup(
                        new KeyboardButton("Организационная поддержка.")
                ));

                this.execute(response);
                this.execute(response1);
                this.execute(response2);
                break;
            }



            case "Государственная служба": {
                var response = new SendMessage(chatId, "Государственная служба:");

                response.replyMarkup(new ReplyKeyboardMarkup(
                        new String[] {"Управление", "Организационная поддержка-2"},
                        new String[] {"Помощь по хозяйству-2", "Служение"}
                ));

                this.execute(response);
                break;
            }

            case "Управление": {
                var response = new SendMessage(chatId, "Управление:");

                response.replyMarkup(new ReplyKeyboardMarkup(
                        new String[] {"Депутат Парламента, Советник Президента"},
                        new String[] {"Министр, Премьер-министр"},
                        new String[] {"Руководитель Администрации, Государственный Секретарь, Заведующий (начальник) приёмной"},
                        new String[] {"Государственная служба"}
                ));

                this.execute(response);
                break;
            }

            case "Депутат Парламента, Советник Президента": {
                var response = new SendMessage(chatId, "Требования: - Формирование политики государства (определение проблем и целей; контроль за выполнением намеченных целей и задач); - Рассмотрение и принятие законов; - Прогнозирование политического состояния государства; - Обеспечение возможности осуществить политические действия в соответствии с планом; - Стратегическое планирование развития государства; - Рассмотрение законов; - Формирование политики государства; - Политическое, экономическое, социальное, культурное прогнозирование");

                var response1 = new SendMessage(chatId, "Благодарю Вас за Ваш выбор. А теперь – три самых важных" +
                        " вопроса... по ссылке");

                var response2 = new SendMessage(chatId, "Для возвращения назад кликните ниже!");

                response1.replyMarkup(new InlineKeyboardMarkup(
                        new InlineKeyboardButton("Ссылка").url("https://docs.google.com/forms/d/e/1FAIpQLSfbj8-v" +
                                "FREt7d9V0iNgY4LSoe2YWnHFndF7GbehyNzI2mvkAw/viewform")
                ));

                response.replyMarkup(new ReplyKeyboardMarkup(
                        new KeyboardButton("Управление")
                ));

                this.execute(response);
                this.execute(response1);
                this.execute(response2);
                break;
            }

            case "Министр, Премьер-министр": {
                var response = new SendMessage(chatId, "Требования: - Руководство определенной сферой государственного управления; - планирование стратегической деятельности в соответствующей сфере; - распоряжение ресурсами; - Реализация политических действий в соответствии с планом; - Формирование политики государства; - Рассмотрение и принятие законов; - Распоряжение государственными ресурсами; - Стратегическое планирование развития государства");

                var response1 = new SendMessage(chatId, "Благодарю Вас за Ваш выбор. А теперь – три самых важных" +
                        " вопроса... по ссылке");

                var response2 = new SendMessage(chatId, "Для возвращения назад кликните ниже!");

                response1.replyMarkup(new InlineKeyboardMarkup(
                        new InlineKeyboardButton("Ссылка").url("https://docs.google.com/forms/d/e/1FAIpQLSfbj8-v" +
                                "FREt7d9V0iNgY4LSoe2YWnHFndF7GbehyNzI2mvkAw/viewform")
                ));

                response.replyMarkup(new ReplyKeyboardMarkup(
                        new KeyboardButton("Управление")
                ));

                this.execute(response);
                this.execute(response1);
                this.execute(response2);
                break;
            }

            case "Руководитель Администрации, Государственный Секретарь, Заведующий (начальник) приёмной": {
                var response = new SendMessage(chatId, "Требования: - Организация работы государственного совета; - Координация внешней политической деятельности государственных органов управления; - координация кадровой политики президента; - представительство от имени организации; - планирование и руководство повседневной деятельностью; - планирование стратегической деятельности в соответствующей сфере; - распоряжение ресурсами");

                var response1 = new SendMessage(chatId, "Благодарю Вас за Ваш выбор. А теперь – три самых важных" +
                        " вопроса... по ссылке");

                var response2 = new SendMessage(chatId, "Для возвращения назад кликните ниже!");

                response1.replyMarkup(new InlineKeyboardMarkup(
                        new InlineKeyboardButton("Ссылка").url("https://docs.google.com/forms/d/e/1FAIpQLSfbj8-v" +
                                "FREt7d9V0iNgY4LSoe2YWnHFndF7GbehyNzI2mvkAw/viewform")
                ));

                response.replyMarkup(new ReplyKeyboardMarkup(
                        new KeyboardButton("Управление")
                ));

                this.execute(response);
                this.execute(response1);
                this.execute(response2);
                break;
            }

            case "Организационная поддержка-2": {
                var response = new SendMessage(chatId, "Организационная поддержка:");

                response.replyMarkup(new ReplyKeyboardMarkup(
                        new String[] {"Главный бухгалтер, Главный аудитор"},
                        new String[] {"Руководитель по осуществлению финансового контроля"},
                        new String[] {"Государственная служба"}
                ));

                this.execute(response);
                break;
            }

            case "Главный бухгалтер, Главный аудитор": {
                var response = new SendMessage(chatId, "Требования: - оценка финансового положения какой-либо сферы государственной деятельности; - проведение анализа финансовой отчетности какой-либо сферы государственной деятельности; - проведение консультаций ; - планирование, управление и координация бухгалтерской деятельности какой-либо государственной организации; - Контроль за финансовыми операциями; - разработка и руководство управленческими и административными процедурами; подготовка финансовых отчетов; подготовка отчетов по прогнозированию доходов");

                var response1 = new SendMessage(chatId, "Благодарю Вас за Ваш выбор. А теперь – три самых важных" +
                        " вопроса... по ссылке");

                var response2 = new SendMessage(chatId, "Для возвращения назад кликните ниже!");

                response1.replyMarkup(new InlineKeyboardMarkup(
                        new InlineKeyboardButton("Ссылка").url("https://docs.google.com/forms/d/e/1FAIpQLSfbj8-v" +
                                "FREt7d9V0iNgY4LSoe2YWnHFndF7GbehyNzI2mvkAw/viewform")
                ));

                response.replyMarkup(new ReplyKeyboardMarkup(
                        new KeyboardButton("Организационная поддержка-2")
                ));

                this.execute(response);
                this.execute(response1);
                this.execute(response2);
                break;
            }

            case "Руководитель по осуществлению финансового контроля": {
                var response = new SendMessage(chatId, "Требования: - планирование, управление и координация финансовой деятельности; - оценка финансового положения;- контроль за финансовыми операциями; - управление бюджетами, обеспечение эффективного использования ресурсов; -представительство от имени организации; - планирование, управление и координация финансовой деятельности; подготовка и предоставление финансовых отчётов; проведение финансовых операций");

                var response1 = new SendMessage(chatId, "Благодарю Вас за Ваш выбор. А теперь – три самых важных" +
                        " вопроса... по ссылке");

                var response2 = new SendMessage(chatId, "Для возвращения назад кликните ниже!");

                response1.replyMarkup(new InlineKeyboardMarkup(
                        new InlineKeyboardButton("Ссылка").url("https://docs.google.com/forms/d/e/1FAIpQLSfbj8-v" +
                                "FREt7d9V0iNgY4LSoe2YWnHFndF7GbehyNzI2mvkAw/viewform")
                ));

                response.replyMarkup(new ReplyKeyboardMarkup(
                        new KeyboardButton("Организационная поддержка-2")
                ));

                this.execute(response);
                this.execute(response1);
                this.execute(response2);
                break;
            }

            case "Помощь по хозяйству-2": {
                var response = new SendMessage(chatId, "Помощь по хозяйству:");

                response.replyMarkup(new ReplyKeyboardMarkup(
                        new String[] {"Финансовый директор", "Бухгалтер, Координатор по бюджету"},
                        new String[] {"Государственная служба"}
                ));

                this.execute(response);
                break;
            }

            case "Финансовый директор": {
                var response = new SendMessage(chatId, "Требования: − планирование, руководство и координация общего функционирования организации; - анализ работы и результатов деятельности организации; − определение целей, стратегий, политики и программ для организации; − разработка и управление бюджетами, контроль расходов и обеспечение эффективного использования ресурсов;; − представительство от имени организации; − подбор или утверждение подбора старших должностных лиц и сотрудников;; − обеспечение соблюдения организацией соответствующего законодательства и нормативных положений; − подготовка или представление отчетности по прогнозированию доходов и бюджету");

                var response1 = new SendMessage(chatId, "Благодарю Вас за Ваш выбор. А теперь – три самых важных" +
                        " вопроса... по ссылке");

                var response2 = new SendMessage(chatId, "Для возвращения назад кликните ниже!");

                response1.replyMarkup(new InlineKeyboardMarkup(
                        new InlineKeyboardButton("Ссылка").url("https://docs.google.com/forms/d/e/1FAIpQLSfbj8-v" +
                                "FREt7d9V0iNgY4LSoe2YWnHFndF7GbehyNzI2mvkAw/viewform")
                ));

                response.replyMarkup(new ReplyKeyboardMarkup(
                        new KeyboardButton("Помощь по хозяйству-2")
                ));

                this.execute(response);
                this.execute(response1);
                this.execute(response2);
                break;
            }

            case "Бухгалтер, Координатор по бюджету": {
                var response = new SendMessage(chatId, "Требования: − подготовка или представление отчетности по прогнозированию доходов и бюджету;; − проведение финансовых исследований по таким вопросам, как предполагаемое мошенничество, несостоятельность и банкротство;; − разработка и контроль систем для определения себестоимости товаров и услуг; - консультирование руководства по финансовым аспектам производительности, портфелям ценных бумаг, проблемам сбыта, новых товаров и тд; − подготовка и заверение финансовых отчетов; − подготовка налоговых деклараций; − подготовка или представление отчетности по прогнозированию доходов и бюджету");

                var response1 = new SendMessage(chatId, "Благодарю Вас за Ваш выбор. А теперь – три самых важных" +
                        " вопроса... по ссылке");

                var response2 = new SendMessage(chatId, "Для возвращения назад кликните ниже!");

                response1.replyMarkup(new InlineKeyboardMarkup(
                        new InlineKeyboardButton("Ссылка").url("https://docs.google.com/forms/d/e/1FAIpQLSfbj8-v" +
                                "FREt7d9V0iNgY4LSoe2YWnHFndF7GbehyNzI2mvkAw/viewform")
                ));

                response.replyMarkup(new ReplyKeyboardMarkup(
                        new KeyboardButton("Помощь по хозяйству-2")
                ));

                this.execute(response);
                this.execute(response1);
                this.execute(response2);
                break;
            }

            case "Культура": {
                var response = new SendMessage(chatId, "Культура:");

                response.replyMarkup(new ReplyKeyboardMarkup(
                        new String[] {"Осмысление", "Производство", "Сохранение"},
                        new String[] {"Организационная помощь", "Служение"}
                ));

                this.execute(response);
                break;
            }

            case "Осмысление": {
                var response = new SendMessage(chatId, "Осмысление:");

                response.replyMarkup(new ReplyKeyboardMarkup(
                        new String[] {"Специалист по генеалогии", "Историк"},
                        new String[] {"Философ-2","Политолог", "Культура"}
                ));

                this.execute(response);
                break;
            }

            case "Специалист по генеалогии": {
                var response = new SendMessage(chatId, "Требования: развитие философских теорий; научные исследования; описание истории; описание природы человеческого опыта; научные разработки; разработки в области гуманитарных наук; междисциплинарные исследования; разработки в области общественных наук.");

                var response1 = new SendMessage(chatId, "Благодарю Вас за Ваш выбор. А теперь – три самых важных" +
                        " вопроса... по ссылке");

                var response2 = new SendMessage(chatId, "Для возвращения назад кликните ниже!");

                response1.replyMarkup(new InlineKeyboardMarkup(
                        new InlineKeyboardButton("Ссылка").url("https://docs.google.com/forms/d/e/1FAIpQLSfbj8-v" +
                                "FREt7d9V0iNgY4LSoe2YWnHFndF7GbehyNzI2mvkAw/viewform")
                ));

                response.replyMarkup(new ReplyKeyboardMarkup(
                        new KeyboardButton("Осмысление")
                ));

                this.execute(response);
                this.execute(response1);
                this.execute(response2);
                break;
            }

            case "Историк": {
                var response = new SendMessage(chatId, "Требования: развитие философских теорий; научные исследования; описание истории; описание природы человеческого опыта; научные разработки; разработки в области гуманитарных наук; междисциплинарные исследования; разработки в области общественных наук.");

                var response1 = new SendMessage(chatId, "Благодарю Вас за Ваш выбор. А теперь – три самых важных" +
                        " вопроса... по ссылке");

                var response2 = new SendMessage(chatId, "Для возвращения назад кликните ниже!");

                response1.replyMarkup(new InlineKeyboardMarkup(
                        new InlineKeyboardButton("Ссылка").url("https://docs.google.com/forms/d/e/1FAIpQLSfbj8-v" +
                                "FREt7d9V0iNgY4LSoe2YWnHFndF7GbehyNzI2mvkAw/viewform")
                ));

                response.replyMarkup(new ReplyKeyboardMarkup(
                        new KeyboardButton("Осмысление")
                ));

                this.execute(response);
                this.execute(response1);
                this.execute(response2);
                break;
            }

            case "Философ-2": {
                var response = new SendMessage(chatId, "Требования: развитие философских теорий; научные исследования; описание истории; описание природы человеческого опыта; научные разработки; разработки в области гуманитарных наук; междисциплинарные исследования; разработки в области общественных наук.");

                var response1 = new SendMessage(chatId, "Благодарю Вас за Ваш выбор. А теперь – три самых важных" +
                        " вопроса... по ссылке");

                var response2 = new SendMessage(chatId, "Для возвращения назад кликните ниже!");

                response1.replyMarkup(new InlineKeyboardMarkup(
                        new InlineKeyboardButton("Ссылка").url("https://docs.google.com/forms/d/e/1FAIpQLSfbj8-v" +
                                "FREt7d9V0iNgY4LSoe2YWnHFndF7GbehyNzI2mvkAw/viewform")
                ));

                response.replyMarkup(new ReplyKeyboardMarkup(
                        new KeyboardButton("Осмысление")
                ));

                this.execute(response);
                this.execute(response1);
                this.execute(response2);
                break;
            }

            case "Политолог": {
                var response = new SendMessage(chatId, "Требования: развитие философских теорий; научные исследования; описание истории; описание природы человеческого опыта; научные разработки; разработки в области гуманитарных наук; междисциплинарные исследования; разработки в области общественных наук.");

                var response1 = new SendMessage(chatId, "Благодарю Вас за Ваш выбор. А теперь – три самых важных" +
                        " вопроса... по ссылке");

                var response2 = new SendMessage(chatId, "Для возвращения назад кликните ниже!");

                response1.replyMarkup(new InlineKeyboardMarkup(
                        new InlineKeyboardButton("Ссылка").url("https://docs.google.com/forms/d/e/1FAIpQLSfbj8-v" +
                                "FREt7d9V0iNgY4LSoe2YWnHFndF7GbehyNzI2mvkAw/viewform")
                ));

                response.replyMarkup(new ReplyKeyboardMarkup(
                        new KeyboardButton("Осмысление")
                ));

                this.execute(response);
                this.execute(response1);
                this.execute(response2);
                break;
            }

            case "Производство": {
                var response = new SendMessage(chatId, "Производство:");

                response.replyMarkup(new ReplyKeyboardMarkup(
                        new String[] {"Звукорежиссёр, Кинорежиссёр, Режиссер документальных фильмов, Оператор-постановщик"},
                        new String[] {"Актёр, Артист драмы, Артист мимического жанра"},
                        new String[] {"Артист спортивно-акробатического жанра, Артист-воздушный гимнаст, Артист цирка, Артист жанра Сатира"},
                        new String[] {"Артист балета, Балетмейстер, Хореограф"},
                        new String[] {"Ведущий программы новостей, Актёр дубляжа, Диктор редакции радиовещания"},
                        new String[] {"Художник-иллюстратор, Художник-живописец, Художник-реставратор"},
                        new String[] {"Разрисовщик фарфоровых и керамических изделий, Клишист, Разрисовщик игрушек"},
                        new String[] {"Координатор трюков, Каскадёр, Суфлёр"},
                        new String[] {"Артист ансамбля песни и танца, Артист-вокалист (оперный и камерный), Артист хора"},
                        new String[] {"Автор сценария, Драматурк, Кинокритик, Очеркист, Писатель, Писатель-романист, Поэт"},
                        new String[] {"Репортер телевизионных/радионовостей, Газетный репортёр, Корреспондент, Журналист"},
                        new String[] {"Культура"}
                ));

                this.execute(response);
                break;
            }

            case "Звукорежиссёр, Кинорежиссёр, Режиссер документальных фильмов, Оператор-постановщик": {
                var response = new SendMessage(chatId, "Требования: 1)изучение сценариев; 2)руководство постановками; 3)организация подготовки; 4)редактирование программ; 1)производство представлений; 2)режиссерская работа; 3)деятельность концертных залов; 4)продюсерская работа.");

                var response1 = new SendMessage(chatId, "Благодарю Вас за Ваш выбор. А теперь – три самых важных" +
                        " вопроса... по ссылке");

                var response2 = new SendMessage(chatId, "Для возвращения назад кликните ниже!");

                response1.replyMarkup(new InlineKeyboardMarkup(
                        new InlineKeyboardButton("Ссылка").url("https://docs.google.com/forms/d/e/1FAIpQLSfbj8-v" +
                                "FREt7d9V0iNgY4LSoe2YWnHFndF7GbehyNzI2mvkAw/viewform")
                ));

                response.replyMarkup(new ReplyKeyboardMarkup(
                        new KeyboardButton("Производство")
                ));

                this.execute(response);
                this.execute(response1);
                this.execute(response2);
                break;
            }

            case "Актёр, Артист драмы, Артист мимического жанра": {
                var response = new SendMessage(chatId, "Требования: 1)запоминание текста; 2)вхождение в роль; 3)участие в репетициях; 4)выступления перед зрителями 1)производство представлений; 2)групповая работа; 3)актерская работа; 4)работа залов.");

                var response1 = new SendMessage(chatId, "Благодарю Вас за Ваш выбор. А теперь – три самых важных" +
                        " вопроса... по ссылке");

                var response2 = new SendMessage(chatId, "Для возвращения назад кликните ниже!");

                response1.replyMarkup(new InlineKeyboardMarkup(
                        new InlineKeyboardButton("Ссылка").url("https://docs.google.com/forms/d/e/1FAIpQLSfbj8-v" +
                                "FREt7d9V0iNgY4LSoe2YWnHFndF7GbehyNzI2mvkAw/viewform")
                ));

                response.replyMarkup(new ReplyKeyboardMarkup(
                        new KeyboardButton("Производство")
                ));

                this.execute(response);
                this.execute(response1);
                this.execute(response2);
                break;
            }

            case "Артист спортивно-акробатического жанра, Артист-воздушный гимнаст, Артист цирка, Артист жанра Сатира": {
                var response = new SendMessage(chatId, "Требования: 1)пересказ смешных историй; 2)развлечение зрителей; 3)представление номеров; 4)дрессировка животных 1)живые выступления; 2)театральные представления; 3)групповая работа; 4)работа залов.");

                var response1 = new SendMessage(chatId, "Благодарю Вас за Ваш выбор. А теперь – три самых важных" +
                        " вопроса... по ссылке");

                var response2 = new SendMessage(chatId, "Для возвращения назад кликните ниже!");

                response1.replyMarkup(new InlineKeyboardMarkup(
                        new InlineKeyboardButton("Ссылка").url("https://docs.google.com/forms/d/e/1FAIpQLSfbj8-v" +
                                "FREt7d9V0iNgY4LSoe2YWnHFndF7GbehyNzI2mvkAw/viewform")
                ));

                response.replyMarkup(new ReplyKeyboardMarkup(
                        new KeyboardButton("Производство")
                ));

                this.execute(response);
                this.execute(response1);
                this.execute(response2);
                break;
            }

            case "Артист балета, Балетмейстер, Хореограф": {
                var response = new SendMessage(chatId, "Требования: 1)создание танцев; 2)исполнение танцев; 3)тренировки и танцевальные занятия; 4)участие в репетициях 1)театральные представления; 2)групповая работа; 3)работа залов; 4)живые представления.");

                var response1 = new SendMessage(chatId, "Благодарю Вас за Ваш выбор. А теперь – три самых важных" +
                        " вопроса... по ссылке");

                var response2 = new SendMessage(chatId, "Для возвращения назад кликните ниже!");

                response1.replyMarkup(new InlineKeyboardMarkup(
                        new InlineKeyboardButton("Ссылка").url("https://docs.google.com/forms/d/e/1FAIpQLSfbj8-v" +
                                "FREt7d9V0iNgY4LSoe2YWnHFndF7GbehyNzI2mvkAw/viewform")
                ));

                response.replyMarkup(new ReplyKeyboardMarkup(
                        new KeyboardButton("Производство")
                ));

                this.execute(response);
                this.execute(response1);
                this.execute(response2);
                break;
            }

            case "Ведущий программы новостей, Актёр дубляжа, Диктор редакции радиовещания": {
                var response = new SendMessage(chatId, "Требования: 1)зачитывание новостей; 2)представление лиц; 3)проведение интервью; 4)изучение материалов 1)производство представлений; 2)групповая работа; 3)актерская работа; 4)работа залов.");

                var response1 = new SendMessage(chatId, "Благодарю Вас за Ваш выбор. А теперь – три самых важных" +
                        " вопроса... по ссылке");

                var response2 = new SendMessage(chatId, "Для возвращения назад кликните ниже!");

                response1.replyMarkup(new InlineKeyboardMarkup(
                        new InlineKeyboardButton("Ссылка").url("https://docs.google.com/forms/d/e/1FAIpQLSfbj8-v" +
                                "FREt7d9V0iNgY4LSoe2YWnHFndF7GbehyNzI2mvkAw/viewform")
                ));

                response.replyMarkup(new ReplyKeyboardMarkup(
                        new KeyboardButton("Производство")
                ));

                this.execute(response);
                this.execute(response1);
                this.execute(response2);
                break;
            }

            case "Художник-иллюстратор, Художник-живописец, Художник-реставратор": {
                var response = new SendMessage(chatId, "Требования: 1) разработка идей; 2) создание рисунков; 3) постановка моделей; 4) восстановление картин 1)творческая работа; 2)воплощение идеи; 3) реставрация произведений искусства; 4)организация выставок");

                var response1 = new SendMessage(chatId, "Благодарю Вас за Ваш выбор. А теперь – три самых важных" +
                        " вопроса... по ссылке");

                var response2 = new SendMessage(chatId, "Для возвращения назад кликните ниже!");

                response1.replyMarkup(new InlineKeyboardMarkup(
                        new InlineKeyboardButton("Ссылка").url("https://docs.google.com/forms/d/e/1FAIpQLSfbj8-v" +
                                "FREt7d9V0iNgY4LSoe2YWnHFndF7GbehyNzI2mvkAw/viewform")
                ));

                response.replyMarkup(new ReplyKeyboardMarkup(
                        new KeyboardButton("Производство")
                ));

                this.execute(response);
                this.execute(response1);
                this.execute(response2);
                break;
            }

            case "Разрисовщик фарфоровых и керамических изделий, Клишист, Разрисовщик игрушек": {
                var response = new SendMessage(chatId, "Требования: 1) рисование узоров; 2)рисование вывесок;3)гравировка узоров; 4)передача эмоций 1)творческая работа; 2)работа художников; 3) реставрация произведений искусства; 4)организация выставок");

                var response1 = new SendMessage(chatId, "Благодарю Вас за Ваш выбор. А теперь – три самых важных" +
                        " вопроса... по ссылке");

                var response2 = new SendMessage(chatId, "Для возвращения назад кликните ниже!");

                response1.replyMarkup(new InlineKeyboardMarkup(
                        new InlineKeyboardButton("Ссылка").url("https://docs.google.com/forms/d/e/1FAIpQLSfbj8-v" +
                                "FREt7d9V0iNgY4LSoe2YWnHFndF7GbehyNzI2mvkAw/viewform")
                ));

                response.replyMarkup(new ReplyKeyboardMarkup(
                        new KeyboardButton("Производство")
                ));

                this.execute(response);
                this.execute(response1);
                this.execute(response2);
                break;
            }

            case "Координатор трюков, Каскадёр, Суфлёр": {
                var response = new SendMessage(chatId, "Требования: 1)постановка спектаклей; 2)исполнение трюков; 3)фокусы; 4)гимнастические номера 1)театральные представления; 2)творческая работа; 3)работа залов; 4)живые выступления");

                var response1 = new SendMessage(chatId, "Благодарю Вас за Ваш выбор. А теперь – три самых важных" +
                        " вопроса... по ссылке");

                var response2 = new SendMessage(chatId, "Для возвращения назад кликните ниже!");

                response1.replyMarkup(new InlineKeyboardMarkup(
                        new InlineKeyboardButton("Ссылка").url("https://docs.google.com/forms/d/e/1FAIpQLSfbj8-v" +
                                "FREt7d9V0iNgY4LSoe2YWnHFndF7GbehyNzI2mvkAw/viewform")
                ));

                response.replyMarkup(new ReplyKeyboardMarkup(
                        new KeyboardButton("Производство")
                ));

                this.execute(response);
                this.execute(response1);
                this.execute(response2);
                break;
            }

            case "Артист ансамбля песни и танца, Артист-вокалист (оперный и камерный), Артист хора": {
                var response = new SendMessage(chatId, "Требования: 1) создание ритма; 2)преобразование идей; 3)игра на музыкальных инструментах; 4)репетиции; 1)театральные представления; 2)творческая работа; 3)работа залов; 4)живые выступления");

                var response1 = new SendMessage(chatId, "Благодарю Вас за Ваш выбор. А теперь – три самых важных" +
                        " вопроса... по ссылке");

                var response2 = new SendMessage(chatId, "Для возвращения назад кликните ниже!");

                response1.replyMarkup(new InlineKeyboardMarkup(
                        new InlineKeyboardButton("Ссылка").url("https://docs.google.com/forms/d/e/1FAIpQLSfbj8-v" +
                                "FREt7d9V0iNgY4LSoe2YWnHFndF7GbehyNzI2mvkAw/viewform")
                ));

                response.replyMarkup(new ReplyKeyboardMarkup(
                        new KeyboardButton("Производство")
                ));

                this.execute(response);
                this.execute(response1);
                this.execute(response2);
                break;
            }

            case "Автор сценария, Драматурк, Кинокритик, Очеркист, Писатель, Писатель-романист, Поэт": {
                var response = new SendMessage(chatId, "Требования: 1)написание произведений; 2)проведение исследований; 3)написание сценариев; 4)выбор материалов для публикации. 1)писательская работа; 2)создание произведений; 3)деятельность авторов; 4)работа залов");

                var response1 = new SendMessage(chatId, "Благодарю Вас за Ваш выбор. А теперь – три самых важных" +
                        " вопроса... по ссылке");

                var response2 = new SendMessage(chatId, "Для возвращения назад кликните ниже!");

                response1.replyMarkup(new InlineKeyboardMarkup(
                        new InlineKeyboardButton("Ссылка").url("https://docs.google.com/forms/d/e/1FAIpQLSfbj8-v" +
                                "FREt7d9V0iNgY4LSoe2YWnHFndF7GbehyNzI2mvkAw/viewform")
                ));

                response.replyMarkup(new ReplyKeyboardMarkup(
                        new KeyboardButton("Производство")
                ));

                this.execute(response);
                this.execute(response1);
                this.execute(response2);
                break;
            }

            case "Репортер телевизионных/радионовостей, Газетный репортёр, Корреспондент, Журналист": {
                var response = new SendMessage(chatId, "Требования: 1)сбор новостей; 2)подготовка комментариев к новостям; 3)проведение исследований; 4)согласование правок 1) предоставление услуг; 2)журналистская работа; 3)деятельность докладчиков; 4)организация выступлений");

                var response1 = new SendMessage(chatId, "Благодарю Вас за Ваш выбор. А теперь – три самых важных" +
                        " вопроса... по ссылке");

                var response2 = new SendMessage(chatId, "Для возвращения назад кликните ниже!");

                response1.replyMarkup(new InlineKeyboardMarkup(
                        new InlineKeyboardButton("Ссылка").url("https://docs.google.com/forms/d/e/1FAIpQLSfbj8-v" +
                                "FREt7d9V0iNgY4LSoe2YWnHFndF7GbehyNzI2mvkAw/viewform")
                ));

                response.replyMarkup(new ReplyKeyboardMarkup(
                        new KeyboardButton("Производство")
                ));

                this.execute(response);
                this.execute(response1);
                this.execute(response2);
                break;
            }

            case "Сохранение": {
                var response = new SendMessage(chatId, "Сохранение:");

                response.replyMarkup(new ReplyKeyboardMarkup(
                        new String[] {"Специалист по учету музейных предметов, Палеограф, Хранитель (произведения искусства), Археограф, Архивариус"},
                        new String[] {"Видеотекарь (высокой квалификации), Библиотекарь-каталогизатор, Библиотекарь, Библеограф"},
                        new String[] {"Библиотечный служащий, Помощник библиотекаря"},
                        new String[] {"Директор (заведующий) библиотеки, Директор художественной галереи, Заведующий архивом, Директор музея"},
                        new String[] {"Руководитель туристического агентства, Заведующий парикмахерской, Директор выставочного цента"},
                        new String[] {"Преподаватель дополнительного образования, Преподаватель бальных танцев, Преподаватель музыкальной школы"},
                        new String[] {"Культура"}
                ));

                this.execute(response);
                break;
            }

            case "Специалист по учету музейных предметов, Палеограф, Хранитель (произведения искусства), Археограф, Архивариус": {
                var response = new SendMessage(chatId, "Требования: 1)сохранение записей; 2)подготовка библиографий; 3) обслуживание собраний предметов; 4)составление классификаций 1)музейная работа; 2)составление коллекций; 3)составление каталогов; 4) обеспечение функционирования исторических мест.");

                var response1 = new SendMessage(chatId, "Благодарю Вас за Ваш выбор. А теперь – три самых важных" +
                        " вопроса... по ссылке");

                var response2 = new SendMessage(chatId, "Для возвращения назад кликните ниже!");

                response1.replyMarkup(new InlineKeyboardMarkup(
                        new InlineKeyboardButton("Ссылка").url("https://docs.google.com/forms/d/e/1FAIpQLSfbj8-v" +
                                "FREt7d9V0iNgY4LSoe2YWnHFndF7GbehyNzI2mvkAw/viewform")
                ));

                response.replyMarkup(new ReplyKeyboardMarkup(
                        new KeyboardButton("Сохранение")
                ));

                this.execute(response);
                this.execute(response1);
                this.execute(response2);
                break;
            }

            case "Видеотекарь (высокой квалификации), Библиотекарь-каталогизатор, Библиотекарь, Библеограф": {
                var response = new SendMessage(chatId, "Требования: 1)пополнение собраний книг; 2)помощь в выборе книг; 3)работа с каталогами; 4)подготовка документов. 1)работа читальных залов; 2) обеспечение деятельности архивов; 3)составление коллекций; 4)подбор информации.");

                var response1 = new SendMessage(chatId, "Благодарю Вас за Ваш выбор. А теперь – три самых важных" +
                        " вопроса... по ссылке");

                var response2 = new SendMessage(chatId, "Для возвращения назад кликните ниже!");

                response1.replyMarkup(new InlineKeyboardMarkup(
                        new InlineKeyboardButton("Ссылка").url("https://docs.google.com/forms/d/e/1FAIpQLSfbj8-v" +
                                "FREt7d9V0iNgY4LSoe2YWnHFndF7GbehyNzI2mvkAw/viewform")
                ));

                response.replyMarkup(new ReplyKeyboardMarkup(
                        new KeyboardButton("Сохранение")
                ));

                this.execute(response);
                this.execute(response1);
                this.execute(response2);
                break;
            }

            case "Библиотечный служащий, Помощник библиотекаря": {
                var response = new SendMessage(chatId, "Требования: 1)пополнение собраний книг; 2)помощь в выборе книг; 3)работа с каталогами; 4)подготовка документов. 1) обеспечение работы читальных залов; 2) обеспечение деятельности архивов; 3)составление коллекций; 4)подбор информации.");

                var response1 = new SendMessage(chatId, "Благодарю Вас за Ваш выбор. А теперь – три самых важных" +
                        " вопроса... по ссылке");

                var response2 = new SendMessage(chatId, "Для возвращения назад кликните ниже!");

                response1.replyMarkup(new InlineKeyboardMarkup(
                        new InlineKeyboardButton("Ссылка").url("https://docs.google.com/forms/d/e/1FAIpQLSfbj8-v" +
                                "FREt7d9V0iNgY4LSoe2YWnHFndF7GbehyNzI2mvkAw/viewform")
                ));

                response.replyMarkup(new ReplyKeyboardMarkup(
                        new KeyboardButton("Сохранение")
                ));

                this.execute(response);
                this.execute(response1);
                this.execute(response2);
                break;
            }

            case "Директор (заведующий) библиотеки, Директор художественной галереи, Заведующий архивом, Директор музея": {
                var response = new SendMessage(chatId, "Требования: 1)руководство учреждением; 2)руководство персоналом; 3)планирование бюджета; 4)подбор персонала 1)управление учреждением; 2)организация деятельности; 3)принятие рещений; 4)оперативный контроль");

                var response1 = new SendMessage(chatId, "Благодарю Вас за Ваш выбор. А теперь – три самых важных" +
                        " вопроса... по ссылке");

                var response2 = new SendMessage(chatId, "Для возвращения назад кликните ниже!");

                response1.replyMarkup(new InlineKeyboardMarkup(
                        new InlineKeyboardButton("Ссылка").url("https://docs.google.com/forms/d/e/1FAIpQLSfbj8-v" +
                                "FREt7d9V0iNgY4LSoe2YWnHFndF7GbehyNzI2mvkAw/viewform")
                ));

                response.replyMarkup(new ReplyKeyboardMarkup(
                        new KeyboardButton("Сохранение")
                ));

                this.execute(response);
                this.execute(response1);
                this.execute(response2);
                break;
            }

            case "Руководитель туристического агентства, Заведующий парикмахерской, Директор выставочного цента": {
                var response = new SendMessage(chatId, "Требования: 1)предоставление услуг; 2)руководство персоналом; 3)планирование бюджета; 4)подбор персонала 1)управление компанией; 2)организация деятельности; 3)принятие рещений; 4)оперативный контроль");

                var response1 = new SendMessage(chatId, "Благодарю Вас за Ваш выбор. А теперь – три самых важных" +
                        " вопроса... по ссылке");

                var response2 = new SendMessage(chatId, "Для возвращения назад кликните ниже!");

                response1.replyMarkup(new InlineKeyboardMarkup(
                        new InlineKeyboardButton("Ссылка").url("https://docs.google.com/forms/d/e/1FAIpQLSfbj8-v" +
                                "FREt7d9V0iNgY4LSoe2YWnHFndF7GbehyNzI2mvkAw/viewform")
                ));

                response.replyMarkup(new ReplyKeyboardMarkup(
                        new KeyboardButton("Сохранение")
                ));

                this.execute(response);
                this.execute(response1);
                this.execute(response2);
                break;
            }

            case "Преподаватель дополнительного образования, Преподаватель бальных танцев, Преподаватель музыкальной школы": {
                var response = new SendMessage(chatId, "Требования: 1)работа кружков; 2)комплектование состава; 3)разработка графиков работы; 4)проведение занятий 1)образование в сфере искусства; 2)организованное обучение; 3)обучение игре на фортепьяно; 4)обучение танцам.");

                var response1 = new SendMessage(chatId, "Благодарю Вас за Ваш выбор. А теперь – три самых важных" +
                        " вопроса... по ссылке");

                var response2 = new SendMessage(chatId, "Для возвращения назад кликните ниже!");

                response1.replyMarkup(new InlineKeyboardMarkup(
                        new InlineKeyboardButton("Ссылка").url("https://docs.google.com/forms/d/e/1FAIpQLSfbj8-v" +
                                "FREt7d9V0iNgY4LSoe2YWnHFndF7GbehyNzI2mvkAw/viewform")
                ));

                response.replyMarkup(new ReplyKeyboardMarkup(
                        new KeyboardButton("Сохранение")
                ));

                this.execute(response);
                this.execute(response1);
                this.execute(response2);
                break;
            }


            case "Организационная помощь": {
                var response = new SendMessage(chatId, "Организационная помощь:");

                response.replyMarkup(new ReplyKeyboardMarkup(
                        new String[] {"Администратор фронт-офиса, Специалист Call-центра"},
                        new String[] {"Архитектор информационных систем, Системный администратор, Сетевой администратор"},
                        new String[] {"Функциональный руководитель по финансовой, административной и юридической деятельности"},
                        new String[] {"Аналитик по вопросам интеллектуальной собственности, Адвокат, Нотариус, Медиатор"},
                        new String[] {"Ассистент оператора (видео-, теле-, звуко-), Кинооператор, Монтажёр"},
                        new String[] {"Координатор выставок, Организатор свадеб, Организатор конференций, Организатор семинаров"},
                        new String[] {"Аукционист, Литературный агент, Театральный агент, Музыкальный агент, Спортивный агент"},
                        new String[] {"Заведующий (руководитель) частью (литературно-драматической, режиссерской"},
                        new String[] {"Кассир казино, Кассир на станции обслуживания, Кассир по выдаче билетов"},
                        new String[] {"Специалист по учету музейных предметов, Служащий библиотеки, Техник музея"},
                        new String[] {"Заведующий билетными кассами, Руководитель канцелярии"},
                        new String[] {"Сотрудник (оператор) службы внутренней безопасности, Охранник, Инкассатор"},
                        new String[] {"Директор съемочной группы, Заведующий редакцией, Заведующий редакцией"},
                        new String[] {"Переводчик (письменный), Переводчик устной речи, Переводчик синхронный, Лингвист"},
                        new String[] {"Продавец-консультант, Демонстратор товара, Торговец, предлагающий товары по месту жительства"},
                        new String[] {"Культура"}
                ));

                this.execute(response);
                break;
            }

            case "Администратор фронт-офиса, Специалист Call-центра": {
                lite(chatId, "Требования: 1)регистрация клиентов; 2)информирование; 3)работа с жалобами; 4) прием платежей 1)подготовка документов; 2)обработка текста; 3)редактирование документов; 4)секретарские услуги", "Организационная помощь");
                    break;
            }

            case "Архитектор информационных систем, Системный администратор, Сетевой администратор": {
                lite(chatId,"Требования: 1) администрирование сетей; 2) диагностика проблем; 3) аварийное восстановление; 4)работа с программным обеспечением 1)разработка компьютерных систем; 2)управление данными; 3)техническая поддержка; 4)настройка компьютеров.","Организационная помощь");
                break;
            }

            case "Функциональный руководитель по финансовой, административной и юридической деятельности": {
                lite(chatId,"Требования: 1)управление организацией; 2)контроль бюджета; 3)подбор персонала; 4)взаимодействие с учреждениями. 1)оперативный контроль; 2)анализ счетов; 3)оформление сделок; 4)подготовка деклараций.","Организационная помощь");
                break;
            }

            case "Аналитик по вопросам интеллектуальной собственности, Адвокат, Нотариус, Медиатор": {
                lite(chatId,"Требования: 1)проведение слушаний; 2) определение прав и обязанностей сторон; 3)вынесение решений; 4)объявление приговора 1)представление интересов сторон; 2)рассмотрение дел; 3)подготовка документов; 4)юридическая работа.","Организационная помощь");
                break;
            }

            case "Ассистент оператора (видео-, теле-, звуко-), Кинооператор, Монтажёр": {
                lite(chatId,"Требования: 1)запись звука; 2)контроль оборудования; 3)решение проблем; 4)контроль систем вещания 1)монтаж и вставка титров; 2) наложение субтитров; 3) компьютерная графика; 4)проявление кинопленки.","Организационная помощь");
                break;
            }

            case "Координатор выставок, Организатор свадеб, Организатор конференций, Организатор семинаров": {
                lite(chatId,"Требования: 1)проведение выставок; 2)оборудование залов; 3)регистрация участников; 4)ведение переговоров 1)реклама выставок; 2)обеспечение персонала; 3)управление персоналом; 4)организация мероприятий","Организационная помощь");
                break;
            }

            case "Аукционист, Литературный агент, Театральный агент, Музыкальный агент, Спортивный агент": {
                lite(chatId,"Требования: 1)получение информации об услугах; 2)заключение договоров; 3)продажи на аукционе; 4)организация туров 1)розничная торговля книгами; 2)розничная торговля товарами; 3)торговля антиквариатом; 4)аукционные дома","Организационная помощь");
                break;
            }

            case "Заведующий (руководитель) частью (литературно-драматической, режиссерской": {
                lite(chatId,"Требования: 1)планирование мероприятий; 2)контроль бюджета; 3)руководство деятельностью; 4)подбор персонала 1)управление заведением; 2)организация деятельности; 3)принятие рещений; 4)оперативный контроль","Организационная помощь");
                break;
            }

            case "Кассир казино, Кассир на станции обслуживания, Кассир по выдаче билетов": {
                lite(chatId,"Требования: 1)получение оплаты; 2)выдача билетов; 3)работа с кассой; 4)регистрация цен 1)проверка оплаты; 2)продажа билетов; 3)информирование; 4)проведение ярмарок","Организационная помощь");
                break;
            }

            case "Специалист по учету музейных предметов, Служащий библиотеки, Техник музея": {
                lite(chatId,"Требования: 1)учет объектов; 2)оформление витрин; 3)составление каталогов; 4)поиск данных 1)информирование посетителей; 2)выдача книг; 3)составление каталогов;","Организационная помощь");
                break;
            }

            case "Заведующий билетными кассами, Руководитель канцелярии": {
                lite(chatId,"Требования: 1)ввод данных; 2)подготовка отчетов; 3)рекомендации работникам; 4)отбор персонала 1)продажа билетов; 2)секретарские услуги; 3)подготовка документов; 4)обработка текста","Организационная помощь");
                break;
            }

            case "Сотрудник (оператор) службы внутренней безопасности, Охранник, Инкассатор": {
                lite(chatId,"Требования: 1)патрулирование помещений; 2)выдача пропусков; 3)сохранение порядка; 4)предотвращение пожаров. 1) услуги телохранителей 2) охрана и патрулирование 3) обеспечение безопасности; 4) инкассация и доставка денег","Организационная помощь");
                break;
            }

            case "Директор съемочной группы, Заведующий редакцией, Заведующий редакцией": {
                lite(chatId,"Требования: 1)производство фильмов; 2)заключение договоров; 3)контроль бюджета; 4)подбор персонала 1)управление компанией; 2)организация деятельности; 3)принятие рещений; 4)оперативный контроль","Организационная помощь");
                break;
            }

            case "Переводчик (письменный), Переводчик устной речи, Переводчик синхронный, Лингвист": {
                lite(chatId,"Требования: 1)написание отчетов; 2)перевод с одного языка на другой; 3)классификация; 4)редактирование материалов 1)научные исследования; 2)творческая работа; 3)междисциплинарные исследования; 4)научные разработки.","Организационная помощь");
                break;
            }

            case "Продавец-консультант, Демонстратор товара, Торговец, предлагающий товары по месту жительства": {
                lite(chatId,"Требования: 1)показ товаров; 2)распространение каталогов; 3) ответы на вопросы 4) регистрация заказов 1) проведение выставок; 2) коммерческих галерей 3) розничная торговля непищевыми продуктами; 4) розничная торговля бытовыми товарами","Организационная помощь");
                break;
            }

            case "Защита": {
                var response = new SendMessage(chatId, "Вы выбрали Защита:");

                response.replyMarkup(new ReplyKeyboardMarkup(
                        "Защита людей", "Здравоохранение", "Сады, парки, редкие растения и животные").resizeKeyboard(true));

                this.execute(response);
                break;
            }

            case "Защита людей": {
                var response = new SendMessage(chatId, "Вы выбрали Защита людей");

                response.replyMarkup(new ReplyKeyboardMarkup(
                        new String[] {"Организационная поддержка - 2", "Социальная защита"},
                        new String[] {"Поддержка по хозяйству", "Обеспечение безопасности", "Защита"}
                ).resizeKeyboard(true));

                this.execute(response);
                break;
            }

            case "Организационная поддержка - 2": {
                var response = new SendMessage(chatId, "Вы выбрали Организационная поддержка");

                response.replyMarkup(new ReplyKeyboardMarkup(
                        new String[] {"Адвокат, Аналитик по вопросам интеллектуальной собственности, Нотариус"},
                        new String[] {"Арбитр, Судья, Виртуальный адвокат, Медиатор социальных конфликтов"},
                        new String[] {"Директор детского дома, Заведующий детским садом, Заведующий социальной службы для детей"},
                        new String[] {"Директор дома престарелых, Директор частной клиники по уходу, Консультант по здоровой старости"},
                        new String[] {"Главный аудитор, Главный бухгалтер, Заведующий приемной, Начальник финансово-экономического отдела"},
                        new String[] {"Руководитель по риск-менеджменту, Управляющий (в коммерческой деятельности)"},
                        new String[] {"Руководитель кредитного отдела, Управляющий страховым агентством"},
                        new String[] {"Директор библиотеки, Директор (заведующий) музея, Заведующий архивом"},
                        new String[] {"Консультант по инвестициям, Специалист по финансовому планированию"},
                        new String[] {"Системный администратор, Сетевой администратор, Дизайнер интерфейсов Архитектор виртуальности"},
                        new String[] {"Торговый брокер, Брокер по транспортировке товаров , Менеджер по управлению онлайн-продажами"},
                        new String[] {"Личный помощник, Секретарь кабинета, Стенографист"},
                        new String[] {"Администратор фронт-офиса, Бортпроводница (на земле), Ресепшионист гостиницы, Секретарь стоматологии"},
                        new String[] {"Защита людей"}
                ).resizeKeyboard(true));

                this.execute(response);
                break;
            }

            case "Адвокат, Аналитик по вопросам интеллектуальной собственности, Нотариус": {
                lite(chatId,"Требования: проведение слушаний; определение прав и обязанностей сторон; вынесение решений; объявление приговора; представление интересов сторон; рассмотрение дел; подготовка документов; юридическая работа.", "Организационная поддержка - 2");
                break;
            }

            case "Арбитр, Судья, Виртуальный адвокат, Медиатор социальных конфликтов": {
                lite(chatId,"Требования: судебные процессы; процессуальные нормы; вынесение решений о виновности; вынесение постановлений суда; деятельность судов; юридическое представительство; разбирательство гражданских дел; назначение исправительных мер.", "Организационная поддержка - 2");
                break;
            }

            case "Директор детского дома, Заведующий детским садом, Заведующий социальной службы для детей": {
                lite(chatId,"Требования: разработка программ развития; контроль бюджета; присмотр за детьми; управление материально-технической базой; управление учреждением; организация деятельности; принятие рещений; оперативный контроль.", "Организационная поддержка - 2");
                break;
            }

            case "Директор дома престарелых, Директор частной клиники по уходу, Консультант по здоровой старости": {
                lite(chatId,"Требования: управление организациями; контроль персонала; реализация программ социального обеспечения; подбор и обучение работников; сестринский уход; помощь в быту; консультации и попечительство; дневной уход.", "Организационная поддержка - 2");
                break;
            }

            case "Главный аудитор, Главный бухгалтер, Заведующий приемной, Начальник финансово-экономического отдела": {
                lite(chatId,"Требования: управление организацией; контроль бюджета; подбор персонала; взаимодействие с учреждениями; оперативный контроль; анализ счетов; оформление сделок; подготовка деклараций", "Организационная поддержка - 2");
                break;
            }

            case "Руководитель по риск-менеджменту, Управляющий (в коммерческой деятельности)": {
                lite(chatId,"Требования: управление рисками; представительство от имени предприятия; управление бюджетом; подбор и подготовка сотрудников; получение денег; социальное планирование; оценка и урегулирование страховых выплат; определение размеров ущерба.", "Организационная поддержка - 2");
                break;
            }

            case "Руководитель кредитного отдела, Управляющий страховым агентством": {
                lite(chatId,"Требования: координация работы персонала; помощь клиентам в решении их финансовых и страховых проблем; мониторинг решений по предоставлению кредита; подготовка отчетности; управление в области налогово-бюджетной политики; рассмотрение фактов нарушений в налоговой сфере; управление в области таможенной политики; контроль расходов.", "Организационная поддержка - 2");
                break;
            }

            case "Директор библиотеки, Директор (заведующий) музея, Заведующий архивом": {
                lite(chatId,"Требования: руководство учреждением; руководство персоналом; планирование бюджета; подбор персонала; управление учреждением; организация деятельности; принятие рещений; оперативный контроль.", "Организационная поддержка - 2");
                break;
            }

            case "Консультант по инвестициям, Специалист по финансовому планированию": {
                lite(chatId,"Требования: определения устойчивости рисков; постановка финансовых целей; подготовка купли-продажи акций и облигаций для клиентов; анализ финансовой эффективности инвестиций; консультирование предприятий по вопросам планирования; планирование в области людских ресурсов; планирование производства и контроля; представительство от имени клиентов перед налоговыми органами.", "Организационная поддержка - 2");
                break;
            }

            case "Системный администратор, Сетевой администратор, Дизайнер интерфейсов Архитектор виртуальности": {
                lite(chatId,"Требования: администрирование сетей; диагностика проблем; аварийное восстановление; работа с программным обеспечением; разработка компьютерных систем; управление данными; техническая поддержка; настройка компьютеров.", "Организационная поддержка - 2");
                break;
            }

            case "Торговый брокер, Брокер по транспортировке товаров , Менеджер по управлению онлайн-продажами": {
                lite(chatId,"Требования: установление контакта между покупателями и продавцами; анализ тенденций рынка; анализ спроса и предложения; ведение переговоров; брокерские операции на фондовой бирже; брокерские операции с ценными бумагами; брокерские операции с товарными контрактами; услуги меняльных контор.", "Организационная поддержка - 2");
                break;
            }

            case "Личный помощник, Секретарь кабинета, Стенографист": {
                lite(chatId,"Требования: печать документов; работа с программами; хранение документов; работа с почтой; подготовка документов; обработка текста; редактирование документов; секретарские услуги.", "Организационная поддержка - 2");
                break;
            }

            case "Администратор фронт-офиса, Бортпроводница (на земле), Ресепшионист гостиницы, Секретарь стоматологии": {
                lite(chatId,"Требования: регистрация клиентов; информирование; работа с жалобами; прием платежей; подготовка документов; обработка текста; редактирование документов; секретарские услуги.", "Организационная поддержка - 2");
                break;
            }

            case "Социальная защита": {
                var response = new SendMessage(chatId, "Вы выбрали Социальная защита");

                response.replyMarkup(new ReplyKeyboardMarkup(
                        new String[] {"Директор центра психолого-педагогической поддержки, Директор центра социального обслуживания"},
                        new String[] {"Руководитель кредитного отдела, Управляющий кредитного учреждения по работе с клиентами"},
                        new String[] {"Агент по оформлению страхования, Страховой брокер"},
                        new String[] {"Консультант по профессиональной реабилитации, Специалист по охране здоровья детей"},
                        new String[] {"Инспектор по претензиям к социальному обеспечению Инспектор, Медиатор социальных конфликтов"},
                        new String[] {"Специалист по оценке ущерба, Страховой эксперт, Актуарий по вопросам страхования"},
                        new String[] {"Брокерский служащий, Кредитный служащий, Служащий по страхованию, Служащий по ценным бумагам"},
                        new String[] {"Управляющий страховым агентством, Страховой брокер, Риск-менеджер"},
                        new String[] {"Служащий по страхованию Служащий по ценным бумагам, Финансовый служащий"},
                        new String[] {"Защита людей"}
                ).resizeKeyboard(true));

                this.execute(response);
                break;
            }

            case "Директор центра психолого-педагогической поддержки, Директор центра социального обслуживания": {
                lite(chatId,"Требования: общее руководство; контроль бюджета; взаимодействие с другими службами; подбор персонала; управление организацией; организация деятельности; принятие рещений; оперативный контроль.", "Социальная защита");
                break;
            }

            case "Руководитель кредитного отдела, Управляющий кредитного учреждения по работе с клиентами": {
                lite(chatId,"Требования: руководство персоналом; консультирование клиентов; финансовые расследования; контроль денежных средств; организационное планирование; предоставление услуг; консультирование по вопросам кредитования; сбор информации.", "Социальная защита");
                break;
            }

            case "Агент по оформлению страхования, Страховой брокер": {
                lite(chatId,"Требования: определение вида и условий страхования; определение величины страховых рисков; объяснение условий и размеров выплат; ведение переговоров о перестраховании; выплата компенсаций; страхование авторских прав; услуги в области страхования; определение размера ущерба.", "Социальная защита");
                break;
            }

            case "Консультант по профессиональной реабилитации, Специалист по охране здоровья детей": {
                lite(chatId,"Требования: проведение бесед; определение типа социальной помощи; программы помощи гражданам; работа с правонарушителями; переподготовка безработных; консультации и попечительство; направление на работу; обеспечение предприятий рабочими.", "Социальная защита");
                break;
            }

            case "Инспектор по претензиям к социальному обеспечению Инспектор, Медиатор социальных конфликтов": {
                lite(chatId,"Требования: государственные услуги; определение размера пособия; информирование заявителей; подготовка протоколов и отчетов; подготовка безработных к работе; консультации и попечительство; направление на работу; обеспечение предприятий рабочими.", "Социальная защита");
                break;
            }

            case "Специалист по оценке ущерба, Страховой эксперт, Актуарий по вопросам страхования": {
                lite(chatId,"Требования: оценка недвижимости; оценка размера ущерба; определение стоимости имущества; подготовка отчетов об оценке. страхование гражданской ответственности; принятие рисков; оценка страховых претензий; установление оценки риска.", "Социальная защита");
                break;
            }

            case "Брокерский служащий, Кредитный служащий, Служащий по страхованию, Служащий по ценным бумагам": {
                lite(chatId,"Требования: выплата страховых компенсаций; начисление процентов; подготовка финансовых документов; ведение учета облигаций; заключение договоров страхования жизни; деятельность страховых агентов; операции с ценными бумагами от имени клиента; урегулирование страховых претензий.", "Социальная защита");
                break;
            }

            case "Управляющий страховым агентством, Страховой брокер, Риск-менеджер": {
                lite(chatId,"Требования: анализ финансовых прогнозов; отслеживание экономических прогнозов; анализ перспективы в сфере инвестиций; анализ информации от банковских фирм; принятие решений; ведение переговоров; деятельность страховых агентов и брокеров; установление размера претензий.", "Социальная защита");
                break;
            }

            case "Служащий по страхованию Служащий по ценным бумагам, Финансовый служащий": {
                lite(chatId,"Требования: ведение данных о сделках; расчет суммы и процентов; начисление процентов; учет акций и других ценных бумаг; ведение программ социального страхования; принятие рисков других страховых компаний; программы социального страхования; ведение инвестиционных фондов.", "Социальная защита");
                break;
            }

            case "Поддержка по хозяйству": {
                var response = new SendMessage(chatId, "Вы выбрали Поддержка по хозяйству");

                response.replyMarkup(new ReplyKeyboardMarkup(
                        new String[] {"Помощник юриста , Судебный распорядитель, Юрисконсульт, Сетевой юрист, Виртуальный адвокат"},
                        new String[] {"Секретарь адвокатской конторы, Секретарь судебного заседания, Юридический секретарь"},
                        new String[] {"Консультант по вопросам семьи и брака, Юридическая и медицинская помощь."},
                        new String[] {"Актуарий, Математик, Статистик"},
                        new String[] {"Координатор по бюджету, Экономист по планированию, Проектировщик индивидуальной финансовой траектории"},
                        new String[] {"Аналитик по страхованию, Консультант по ценным бумагам, Кредитный аналитик, Финансовый риск-менеджер"},
                        new String[] {"Кредитный агент, Кредитный брокер, Агент по кредитованию"},
                        new String[] {"Оценщик, Оценщик недвижимости, Эксперт по оценке страховых убытков"},
                        new String[] {"Валютный дилер, Инвестиционный брокер, Мультивалютный переводчик, Корпоративный антрополог"},
                        new String[] {"Ассистент бухгалтера, Помощник бухгалтера, Счетовод"},
                        new String[] {"Защита людей"}
                ).resizeKeyboard(true));

                this.execute(response);
                break;
            }

            case "Помощник юриста , Судебный распорядитель, Юрисконсульт, Сетевой юрист, Виртуальный адвокат": {
                lite(chatId,"Требования: оформление судебных процедур; вручение исков; подготовка юридических документов; изучение документации; деятельность нотариусов и судебных приставов; ведение дел по имуществу; представительство в учреждениях; вынесение судебных решений.", "Поддержка по хозяйству");
                break;
            }

            case "Секретарь адвокатской конторы, Секретарь судебного заседания, Юридический секретарь": {
                lite(chatId,"Требования: подготовка правовых документов; рассмотрение документов; рассылка документов; передача недвижимости; составление писем и почтовая рассылка; ведение стенографических отчетов; расшифровка документов; редактирование документов.", "Поддержка по хозяйству");
                break;
            }

            case "Консультант по вопросам семьи и брака, Юридическая и медицинская помощь.": {
                lite(chatId,"Требования: помощь людям в трудной ситуации; адаптация и социальная реабилитация; консультации и лечение; финансовая; консультации, попечительство, помощь беженцам; воспитательная деятельность с детьми; консультации по вопросам брака и семьи; дневной уход за детьми с недостатками.", "Поддержка по хозяйству");
                break;
            }

            case "Актуарий, Математик, Статистик": {
                lite(chatId,"Требования: разработка и внедрение программ страхования; анализ систем управления; применение математических принципов в статистике; анализ статистических данных; планирование работы статистических служб; изучение возможностей рынка; исследование покупателей; оценка страховых претензий.", "Поддержка по хозяйству");
                break;
            }

            case "Координатор по бюджету, Экономист по планированию, Проектировщик индивидуальной финансовой траектории": {
                lite(chatId,"Требования: бухгалтерский контроль; подготовка финансовых отчетов; отчёты по доходам; консультирование руководства; подготовка счетов; анализ счетов; подготовка деклараций; консультации.", "Поддержка по хозяйству");
                break;
            }

            case "Аналитик по страхованию, Консультант по ценным бумагам, Кредитный аналитик, Финансовый риск-менеджер": {
                lite(chatId,"Требования: мониторинг экономических и промышленных изменений; принятие решений о капиталовложениях; финансовый анализ; оценка рисков; изучение эффективности производства; аналитика совокупности ценных бумаг; консультации для коммерческих предприятий; консультации государственным организациям в области планирования.", "Поддержка по хозяйству");
                break;
            }

            case "Кредитный агент, Кредитный брокер, Агент по кредитованию": {
                lite(chatId,"Требования: переговоры с контрагентом о кредите; оценка кредитной истории; утверждение или отклонение кредитных заявок; подготовка писем по оплате счетов; деятельность банков; услуги почтовых систем банков; предоставление кредитов; финансирование международной торговли.", "Поддержка по хозяйству");
                break;
            }

            case "Оценщик, Оценщик недвижимости, Эксперт по оценке страховых убытков": {
                lite(chatId,"Требования: определение стоимости объекта недвижимости; оценка размера ущерба; определение стоимости аналогичных товаров; подготовка отчетов об оценке; предоставление страховых услуг; установление размера выплат по страховому случаю; оценка риска и ущерба; посреднические услуги при оценке стоимости имущества.", "Поддержка по хозяйству");
                break;
            }

            case "Валютный дилер, Инвестиционный брокер, Мультивалютный переводчик, Корпоративный антрополог": {
                lite(chatId,"Требования: получение информации о финансах клиентов и компаний; анализ тенденций рынка ценных бумаг; информирование клиентов; оформление и передача документов по сделкам; осуществление операций на финансовых рынках; брокерские операции с ценными бумагами; брокерские операции с товарными контрактами; аналитика ценных бумаг.", "Поддержка по хозяйству");
                break;
            }

            case "Ассистент бухгалтера, Помощник бухгалтера, Счетовод": {
                lite(chatId,"Требования: подготовка документов; учет финансовых операций; ведение документов по финансовым операциям; бухгалтерский расчет на компьютере. бухгалтерский учет; ведение бухгалтерских книг; проведение расчетов; анализ счетов и подтверждение их точности.", "Поддержка по хозяйству");
                break;
            }

            case "Обеспечение безопасности": {
                var response = new SendMessage(chatId, "Вы выбрали Обеспечение безопасности");

                response.replyMarkup(new ReplyKeyboardMarkup(
                        new String[] {"Лесной пожарный, Пожарный", "Полицейский"},
                        new String[] {"Тюремный охранник, Конвоир", "Охранник, Инкассатор"},
                        new String[] {"Кинолог, Матрос-спасатель, Спасатель в горах, Спасатель-водолаз"},
                        new String[] {"Егерь, Смотритель заповедника, Детектив", "Защита людей"}
                ).resizeKeyboard(true));

                this.execute(response);
                break;
            }

            case "Лесной пожарный, Пожарный": {
                lite(chatId,"Требования: борьба с пожаром; поддержание порядка; патрулирование помещений; поддержание порядка на мероприятиях; деятельность полицейских сил; деятельность пожарных бригад; деятельность судов; деятельность во время стихийных бедствий.", "Обеспечение безопасности");
                break;
            }

            case "Полицейский": {
                lite(chatId,"Требования: поддержание порядка; арест преступников; регулирование дорожного движения; оказание неотложной помощи; охрана порядка; работа полицейских сил; береговая охрана; регулирование дорожного движения.", "Обеспечение безопасности");
                break;
            }

            case "Тюремный охранник, Конвоир": {
                lite(chatId,"Требования: обыск заключенных; осмотр камер; надзор за заключенными; патрулирование территории тюрьмы; охрана порядка; работа полицейских сил; береговая охрана; охрана и патрулирование.", "Обеспечение безопасности");
                break;
            }

            case "Охранник, Инкассатор": {
                lite(chatId,"Требования: патрулирование помещений; выдача пропусков; сохранение порядка; предотвращение пожаров; услуги телохранителей; охрана и патрулирование; обеспечение безопасности; инкассация и доставка денег", "Обеспечение безопасности");
                break;
            }

            case "Кинолог, Матрос-спасатель, Спасатель в горах, Спасатель-водолаз": {
                lite(chatId,"Требования: патрулирование пляжей; поиск пострадавших; доврачебная помощь; объяснение правил поведения; спасение людей и животных; оказание помощи; обеспечение запасов для использования; береговая охрана.", "Обеспечение безопасности");
                break;
            }

            case "Егерь, Смотритель заповедника, Детектив": {
                lite(chatId,"Требования: розыск по заказу клиента; сбор информации; надзор за охотой; наблюдение за животными; расследование и сыск; работа частных детективов; расследование нарушений; защита лесов.", "Обеспечение безопасности");
                break;
            }

            case "Здравоохранение": {
                var response = new SendMessage(chatId, "Вы выбрали Здравоохранение");

                response.replyMarkup(new ReplyKeyboardMarkup(
                        new String[] {"Организационная поддержка - 3", "Поддержка по хозяйству - 2"},
                        new String[] {"Защита"}
                ).resizeKeyboard(true));

                this.execute(response);
                break;
            }

            case "Организационная поддержка - 3": {
                var response = new SendMessage(chatId, "Вы выбрали Организационная поддержка - 3");

                response.replyMarkup(new ReplyKeyboardMarkup(
                        new String[] {"Врач-терапевт", "Врач общей практики"},
                        new String[] {"Врач общий хирург, Врач-кардиохирург, Врач-нейрохирург , Врач-хирург пластический"},
                        new String[] {"Врач акушер-гинеколог", "Врач-вирусолог", "Врач-невролог"},
                        new String[] {"Врач-психиатр", "Врач-пульмонолог", "Врач-стоматолог"},
                        new String[] {"Врач-рентгенолог, Врач-томограф, Врач лучевой диагностики, Врач ультразвуковой диагностики"},
                        new String[] {"Врач-гомеопат, Врач-рефлексотерапевт, Врач мануальный терапевт, Народный целитель, Фитотерапевт"},
                        new String[] {"Ветеринарный врач, Ветеринарный хирург, Ветеринарный фельдшер"},
                        new String[] {"Специалист санитарно-эпидемиологической службы-биолог, Ветеринарно-санитарный врач"},
                        new String[] {"Врач общественного здравоохранения", "Здравоохранение"}
                ).resizeKeyboard(true));

                this.execute(response);
                break;
            }

            case "Врач-терапевт": {
                lite(chatId,"Требования: проведение консультаций для пациентов с неустановленными заболеваниями; осмотр пациентов для первичной диагностики заболеваний; подбор подходящих методов диагностики; направление на обследования; постановка предварительного диагноза; подбор способов лечения заболеваний; наблюдение за пациентами во время лечения; направление пациентов к узким специалистам.", "Организационная поддержка - 3");
                break;
            }

            case "Врач общей практики": {
                lite(chatId,"Требования: проведение консультаций на тему различных распространённых заболеваний; осмотр пациентов по вопросам различных распространённых заболеваний; подбор подходящих методов диагностики; направление на обследования; постановка предварительного диагноза; подбор способов лечения заболеваний; наблюдение за пациентами во время лечения; направление пациентов в больницы и поликлиники.", "Организационная поддержка - 3");
                break;
            }

            case "Врач общий хирург, Врач-кардиохирург, Врач-нейрохирург , Врач-хирург пластический": {
                lite(chatId,"Требования: проведение консультаций на тему заболеваний, требующих хирургического вмешательства; осмотр пациентов по вопросам заболеваний, требующих хирургического вмешательства; подбор подходящих методов диагностики; подготовка пациентов к операциям; проведение операций; наблюдение за пациентами после операций; направление на обследования; изучение информации о пациенте от других врачей.", "Организационная поддержка - 3");
                break;
            }

            case "Врач акушер-гинеколог": {
                lite(chatId,"Требования: проведение консультаций на тему заболеваний, требующих хирургического вмешательства; осмотр пациентов по вопросам заболеваний, требующих хирургического вмешательства; подбор подходящих методов диагностики; подготовка пациентов к операциям; проведение операций; наблюдение за пациентами после операций; направление на обследования; изучение информации о пациенте от других врачей.", "Организационная поддержка - 3");
                break;
            }

            case "Врач-вирусолог": {
                lite(chatId,"Требования: проведение консультаций на тему вирусных заболеваний; осмотр пациентов по вопросам вирусных заболеваний; подбор подходящих методов диагностики; направление на обследования; постановка предварительного диагноза; подбор способов лечения заболеваний; наблюдение за пациентами во время лечения; изучение информации о пациенте от других врачей.", "Организационная поддержка - 3");
                break;
            }

            case "Врач-невролог": {
                lite(chatId,"Требования: проведение консультаций на тему заболеваний нервной системы; осмотр пациентов по вопросам заболеваний нервной системы; подбор подходящих методов диагностики; направление на обследования; постановка предварительного диагноза; подбор способов лечения заболеваний; наблюдение за пациентами во время лечения; изучение информации о пациенте от других врачей.", "Организационная поддержка - 3");
                break;
            }

            case "Врач-психиатр": {
                lite(chatId,"Требования: проведение консультаций на тему психических расстройств; осмотр пациентов по вопросам психических расстройств; подбор подходящих методов диагностики; направление на обследования; постановка предварительного диагноза; подбор способов лечения заболеваний; наблюдение за пациентами во время лечения; изучение информации о пациенте от других врачей.", "Организационная поддержка - 3");
                break;
            }

            case "Врач-пульмонолог": {
                lite(chatId,"Требования: проведение консультаций на тему заболеваний лёгких; осмотр пациентов по вопросам заболеваний лёгких; подбор подходящих методов диагностики; направление на обследования; постановка предварительного диагноза; подбор способов лечения заболеваний; наблюдение за пациентами во время лечения; изучение информации о пациенте от других врачей.", "Организационная поддержка - 3");
                break;
            }

            case "Врач-стоматолог": {
                lite(chatId,"Требования: проведение консультаций на тему заболеваний зубов и полости рта; осмотр пациентов по вопросам заболеваний зубов и полости рта; подбор подходящих методов диагностики; направление на обследования; проведение операций и манипуляций; назначение лекарств; изготовление и установка зубных протезов; подбор способов лечения заболеваний.", "Организационная поддержка - 3");
                break;
            }

            case "Врач-рентгенолог, Врач-томограф, Врач лучевой диагностики, Врач ультразвуковой диагностики": {
                lite(chatId,"Требования: определение подходящих методов диагностики; проведение диагностики; работа с оборудованием; проведение исследований в лабораторииАнализ результатов диагностики; выявление патологий; подготовка заключения; контроль за процессом диагностики и за действиями других работников.", "Организационная поддержка - 3");
                break;
            }

            case "Врач-гомеопат, Врач-рефлексотерапевт, Врач мануальный терапевт, Народный целитель, Фитотерапевт": {
                lite(chatId,"Требования: осмотр пациентов по вопросам заболеваний, которые можно вылечить нетрадиционными методами; диагностирование заболеваний; подготовка плана лечения; проведение манипуляций; проведение консультаций на тему заболеваний, которые можно вылечить нетрадиционными методами; назначение препаратов и методов лечения; изготовление лекарственных средств; контактирование с медицинскими работниками по вопросам лечения пациентов; проведение исследований в своей области.", "Организационная поддержка - 3");
                break;
            }

            case "Ветеринарный врач, Ветеринарный хирург, Ветеринарный фельдшер": {
                lite(chatId,"Требования: проведение консультаций на тему заболеваний у животных и ухода за животными; осмотр животных для первичной диагностики заболеваний; подбор подходящих методов диагностики; проведение диагностикиПостановка диагноза; назначение лечения; проведение операций и манипуляций; приём родов у животных.", "Организационная поддержка - 3");
                break;
            }

            case "Специалист санитарно-эпидемиологической службы-биолог, Ветеринарно-санитарный врач": {
                lite(chatId,"Требования: контакты с государственными органами; участие в профилактике инфекционных заболеваний; участие в решении вопросов об утилизации отходов и сточных вод; проведение консультаций по утилизации опасных веществ; проведение консультаций по вопросам соблюдения законов в сфере экологии; проведение экологических исследований; участие в ликвидации нарушений экологической обстановки; контроль условий производства продовольствия.", "Организационная поддержка - 3");
                break;
            }

            case "Врач общественного здравоохранения": {
                lite(chatId,"Требования: решение вопросов в сфере здравоохранения на уровне D региона или страны; участие в управлении органами власти в сфере здравоохранения; взаимодействие с руководством больниц; распространение информации о заболеваниях среди населения; участие в мероприятиях по профилактике заболеваний; координация действий специалистов при борьбе со вспышками заболеваний; участие в изменении системы здравоохранения города, региона или страны; проведение научных исследований в сфере медицины.", "Организационная поддержка - 3");
                break;
            }

            case "Поддержка по хозяйству - 2": {
                var response = new SendMessage(chatId, "Вы выбрали Поддержка по хозяйству - 2");

                response.replyMarkup(new ReplyKeyboardMarkup(
                        new String[] {"Медицинская(ий) сестра/брат.", "Провизор, Фармацевт"},
                        new String[] {"Научный сотрудник, Фармаколог", "Здравоохранение"}
                ).resizeKeyboard(true));

                this.execute(response);
                break;
            }

            case "Медицинская(ий) сестра/брат.": {
                lite(chatId,"Требования: информирование пациентов о ходе лечения; уход за пациентами; проведение процедур и манипуляций; помощь врачам при проведении консультаций; наблюдение за пациентами; очистка ран и наложение повязок; помощь в медицинских осмотрах; помощь врачам в записи истории болезни.", "Поддержка по хозяйству - 2");
                break;
            }

            case "Провизор, Фармацевт": {
                lite(chatId,"Требования: работа с рецептами, выписанными врачами; ознакомление с историями болезни; подбор лекарств, проверка препаратов на совместимость; определение оптимальной дозировки лекарств; приготовление препаратов и контроль их приготовления; консультирование врачей по вопросам использования препаратов; сотрудничество с представителями больниц и поликлиник по вопросам лечения пациентов; обеспечение условий хранения препаратов в аптеке.", "Поддержка по хозяйству - 2");
                break;
            }

            case "Научный сотрудник, Фармаколог": {
                lite(chatId,"Требования: проведение исследований в области медицины по выбранному направлению; изучение опыта исследований других учёных и врачей; участие в применении результатов исследований на практике; создание методических материалов для учёных и врачей; составление отчётов о научной работе; контроль деятельности других научных сотрудников; участие в мероприятиях по обмену опытом с другими учёными и врачами; создание новых лекарств и разработка новых методов лечения.", "Поддержка по хозяйству - 2");
                break;
            }

            case "Сады, парки, редкие растения и животные": {
                var response = new SendMessage(chatId, "Вы выбрали Сады, парки, редкие растения и животные");

                response.replyMarkup(new ReplyKeyboardMarkup(
                        new String[] {"Помощь по хозяйству - 2", "Организационная поддержка - 4"},
                        new String[] {"Поддержка знаний - 2", "Защита"}
                ).resizeKeyboard(true));

                this.execute(response);
                break;
            }

            case "Помощь по хозяйству - 2": {
                var response = new SendMessage(chatId, "Вы выбрали Помощь по хозяйству - 2");

                response.replyMarkup(new ReplyKeyboardMarkup(
                        new String[] {"Садовод", "Садовник", "Техник-садовод", "Техник-лесовод"},
                        new String[] {"Тракторист", "Машинист дренажной машины", "Машинист уборочных машин"},
                        new String[] {"Квалифицированный сельскохозяйственный рабочий", "Техник-геолог"},
                        new String[] {"Техник по мониторингу загрязнения окружающей среды", "Бухгалтер - 2"},
                        new String[] {"Неквалифицированный рабочий", "Мусорщик (Уборщик)", "Сады, парки, редкие растения и животные"}
                ).resizeKeyboard(true));

                this.execute(response);
                break;
            }

            case "Садовод": {
                lite(chatId,"Требования: подбор саженцев и семян согласно климату и пожеланиям заказчиков; пересадка и удаление растений на основании пожеланий заказчика; защита деревьев от вредителей насекомых, животных и растений; использование сельскохозяйственных машин для обработки и подготовки почвы; планирование и создание садов, парков; создание систем ирригации и дренажа почвы для эффективного полива сада; посадка саженцев деревьев, кустарников, цветов и других растений; подбор и применение удобрений.", "Помощь по хозяйству - 2");
                break;
            }

            case "Садовник": {
                lite(chatId,"Требования: выращивание рассады для декоративных целей; обрезка деревьев и кустарников; защита парков и садов от вредителей с помощью различных средств; своевременный полив растений; формирование внешнего вида растений в садах и парках на основании пожеланий заказчиков; подкормка растений; проверка здоровья растений и деревьев, выявление и обработка сорняков; удаление или лечение больных деревьев.", "Помощь по хозяйству - 2");
                break;
            }

            case "Техник-садовод": {
                lite(chatId,"Требования: выращивание посадочного материала на заказ; проверка и оценка качества выращенной продукции; контроль за качеством обслуживания садов; консультирование заказчиков в области садовых культур; сбор данных и оценка затрат на реализацию проектов садов и парков; сбор образцов почвы для анализов и экспериментов; проведение прикладных исследований о составе почвы для разработки проекта сада; выявление новых видов вредителей садовых культур и разработка методов борьбы с ними.", "Помощь по хозяйству - 2");
                break;
            }

            case "Техник-лесовод": {
                lite(chatId,"Требования: выращивание саженцев на заказ; проверка и оценка качества выращенной продукции; консультирование заказчиков в области лесопосадок и парков; проведение прикладных исследований для формирования предложений о создании парка;Сбор данных и оценка затрат на реализацию проектов садов и парков;Выявление новых видов вредителей хвойных и лиственных деревьев, и разработка методов борьбы с ними;Контроль за качеством обслуживания парков.Сбор образцов почвы для анализов и экспериментов.", "Помощь по хозяйству - 2");
                break;
            }

            case "Тракторист": {
                lite(chatId,"Требования: расчистка территории от заваловкорчевание деревьев, перевозка лесоматериалов; проведение земельных работрытье котлованов и траншей для прокладки коммуникаций; пересадка саженцев деревьев и иных растенийвождение и обслуживание машин на тракторной тяге отличающейся различными встроенными приспособлениями; подготовка техники к работе; обслуживание техники.", "Помощь по хозяйству - 2");
                break;
            }

            case "Машинист дренажной машины": {
                lite(chatId,"Требования: удаление камней и иных крупных объектов мешающих осуществлению дренажных работ; осушение территорий с помощью специальной машиныЗемляные работы, создание траншей; разбираться в чертежах и планах местности, четко понимать размеры требуемых раскопов; укладка труб; умение работать на различных видах спец.техники: дренажный экскаватор, трубоукладчик, бульдозер; подготовка дренажной машины к работе; обслуживание техники и проведение мелкого ремонта.", "Помощь по хозяйству - 2");
                break;
            }

            case "Машинист уборочных машин": {
                lite(chatId,"Требования: уборка территорий парка от небольшого мусора и пыли в том числе в недоступных для проезда местах; соблюдение требований передвижение техники по парку; своевременная проверка наполненности емкости с мусором и его последующая утилизация; знание территории необходимой для уборки, чистки и мытья; умение работать на различных видах малогабаритных уборочных машин; соблюдение графика уборки территории; подготовка техники к работе; обслуживание техники и проведение мелкого ремонта.", "Помощь по хозяйству - 2");
                break;
            }

            case "Квалифицированный сельскохозяйственный рабочий": {
                lite(chatId,"Требования: подготавливает почву к посадкам, в том числе занимается удобрением, подкормкой и формированием плодородного слоя; занимается выращиванием порученных ему растений; осуществление сбора урожая с плодовых деревьев в ботанических садах и городских парках прореживание растений, уничтожение сорняков, обрезка деревьев и кустарниковобработка растений и почвы средствами от вредителей и патогенных микроорганизмоввыявление и ликвидация больных растенийнастройка и эксплуатация ирригационного оборудования и поддержка его в чистоте и работоспособном состоянии; занимается эксплуатацией и обслуживанием используемого сельскохозяйственного оборудования.", "Помощь по хозяйству - 2");
                break;
            }

            case "Техник-геолог": {
                lite(chatId,"Требования: наблюдение за ходом экспериментовсоставление отчетов, фиксация записи результатовэксплуатация и обслуживанием лабораторных приборов и оборудования; подготовка материалов для экспериментов (замораживание, изготовление образцов и смешивание химических веществ); сбор и проверка проб земли и воды, запись наблюдений и анализ данных в помощь геохимикам, ботаникам и агрономам; проведение геологической разведки (взятие образцов грунта и проведение геологических изысканий на наличие водных пластов и подземных пустот); осуществление геохимических исследований состава почвыведение мониторинга состояния подземных и поверхностных водисследование полезных ископаемых и горных пород лабораторными методами.", "Помощь по хозяйству - 2");
                break;
            }

            case "Техник по мониторингу загрязнения окружающей среды": {
                lite(chatId,"Требования: оказание технической помощи инженерам при проектировании систем восстановления и сохранения чистоты воды, воздуха или почвы; проведение предварительной подготовки лабораторного оборудования и лаборатории в целом, для проведения анализов; выполнение лабораторных исследований образцов на наличие различных примесей, вредных веществ или их отсутствия; регулирования и калибровка лабораторного оборудования; сбор данных для построения прогнозов о загрязнении воздуха и окружающей среды; определение уровней загрязнения окружающей среды с использованием специального оборудования для анализа воздуха и воды; создание информационных сводок об уровнях загрязнения определенных территорий, водоемов; проведение полевых работ и изысканий в области гидрометеорологии и смежных с ней областях.", "Помощь по хозяйству - 2");
                break;
            }

            case "Неквалифицированный рабочий": {
                lite(chatId,"Требования: уход за садами и парками посредством прополки газонов и пересадки растений (под руководством квалифицированных сотрудников); выполнение уборки мусора, хлама и т.д. на территории садов, парков и природоохранных территорий; выполнение работ по поливу и подкормке деревьев в городских условиях; обслуживание зданий и территорий парковых комплексов, проведение подсобных работ на хозяйстве;ведение деятельности по обслуживанию зданий и территорий с зелеными насаждениями; уход за почвой и территорией для сохранения их в чистоте; проведение покоса травы, на указанной площади, для создания ухоженного вида территории; проведение вспомогательных работ по озеленению зданий спортивных площадок и водных пространств.", "Помощь по хозяйству - 2");
                break;
            }

            case "Мусорщик (Уборщик)": {
                lite(chatId,"Требования: сбор мусора и материалов для вторичной переработки в урны и грузовые контейнеры; контроль чистоты и уборка улиц, парков и других общественных местИспользование для работы специальные мусоровозы для вывоза мусора; вывоз и утилизация мусора с территории парков, садов и иных территорий на специальные мусороперерабатывающие предприятия; проведение работ по очистке льда и снега с подконтрольной территории; уборка мусора, грязи и пыли снаружи зданий всех типов; осуществление услуг по очистке и обслуживанию плавательных бассейнов; проведение дезинфекцию и дезинсекцию помещений и территорий при необходимости.", "Помощь по хозяйству - 2");
                break;
            }

            case "Бухгалтер - 2": {
                lite(chatId,"Требования: осуществление выплат и списаний средств по выставленным счетам; расчет текущего бюджета организации, точное расходование выделенных средств и как следствие формирование налогового отчета о финасовой деятельности организации; разработка новых методы бухгалтерского учета; проверка отчетности; осуществление бухгалтерского учета и расходования средств организации; ведение перерасчета средств, создание отчетов для налоговой службы, подготовка необходимой документации для проведения финансового аудитаПодготовка и проверка финансовых отчётов; формирование и ведение отчетности на базе лицензированных компьютерных программ.", "Помощь по хозяйству - 2");
                break;
            }

            case "Организационная поддержка - 4": {
                var response = new SendMessage(chatId, "Вы выбрали Организационная поддержка - 4");

                response.replyMarkup(new ReplyKeyboardMarkup(
                        new String[] {"Ботаник", "Эколог", "Инспектор особо охраняемых природных территорий"},
                        new String[] {"Агроном-дендролог", "Проектировщик городской инфраструктуры"},
                        new String[] {"Проектировщик садово-парковых ансамблей", "Инженер строитель"},
                        new String[] {"Инженер по охране окружающей среды", "Гид", "Кассир"},
                        new String[] {"Директор ботанического сада", "Директор парка культуры и отдыха"},
                        new String[] {"Начальник финансово-экономического отдела", "Директор по персоналу"},
                        new String[] {"Заведующий хозяйством", "Сады, парки, редкие растения и животные"}
                ).resizeKeyboard(true));

                this.execute(response);
                break;
            }

            case "Ботаник": {
                lite(chatId,"Требования: проведение экспериментов, в том числе по выведению новых видов растений; создание и публикация отчетов и научных работ на основании проведенных исследований; участие в публичных выступлениях и ведение лекций с целью популяризации науки и существующих угроз для определенных видов растений, а так же способов решения проблемы; создание описаний новых растений, пополнение информации о ранее изученных; проведение лабораторных и полевых исследований с целью проверки гипотез об опасности тех или иных природных явлений или влиянии человека на ухудшение природы; построение прогнозов роста загрязнений атмосферного воздуха; обоснование нормативов допустимых выбросов или сбросов отходов в водусоздание и публикация отчетов и научных работ на основании проведенных исследований.", "Организационная поддержка - 4");
                break;
            }

            case "Эколог": {
                lite(chatId,"Требования: сбор образцов и фактов, характеризующих состояние и развитие неблагоприятных условий для жизни и здоровья человека и всех остальных живых существ; поиск способов снижения вредоносных факторов различных производств; предоставление консультационных услуг в области экологии для представителей государственной власти и промышленного сектора; создание планов по сохранению и восстановлению редких и исчезающих видов растений и животных, целых экосистем; осуществляет государственный надзор и контроль за деятельностью посетителей территорий; инициирует меры по поддержанию или улучшению санитарно-гигиенических условий и предотвращению загрязнения воды, воздуха и почвы; участвует в разработке и внедрении эффективных методов охраны природы и поддержание экологического баланса в заповедниках и природных парках; занимается выявлением нарушений природопользования и загрязнения окружающей среды (в том числе различными производствами, которые негативно влияют на подведомственные инспектору территории).", "Организационная поддержка - 4");
                break;
            }

            case "Инспектор особо охраняемых природных территорий": {
                lite(chatId,"Требования: инициирование судебных процессов в адрес физических или юридических лиц допустивших загрязнение окружающей среды, сбор и подготовка материалов, передача их в вышестоящие инстанции; выявление угроз лесных пожаров и пресечением установленных нарушений; осуществляет контроль за соблюдением законодательства в сфере рыболовства и лесопользования, выявление фактов браконьерства и незаконной вырубки; занимается консультированием предприятий по вопросам реализации государственных правил экологической безопасности производства и решением проблемы утилизации отходов производства; занимается выращиванием леса, лесопосадок, отбором и пересадкой деревьев; предоставление услуг в области лесоводства: консультирование заказчиков, оценка лесоматериала и составление отчетов; создание коллекций растений в целях сохранения и разнообразия и обогащения растительного мираВедение разработки и внедрения научных методов охраны лесов, заповедников и экопарков.", "Организационная поддержка - 4");
                break;
            }

            case "Агроном-дендролог": {
                lite(chatId,"Требования: изучение факторов окружающей среды, влияющие на рост деревьев и формирование лесопосадок; исследование особенности почвы и изучение возможностей её эффективного использования; изучение способов распространения и восстановления лесных пород, методы улучшения роста деревьев; ведение научной, учебной и просветительской деятельности; оказание консультационных услуг по формированию архитектурных решений: анализ и оценка территорий, проектирование облика города; осуществление территориального планирования; занимается планированием и координацией отвода земельных угодий под парки, школы, учреждения, аэропорты, шоссе и сопутствующие проекты; оказание услуг по управлению проектами строительства, выполнению строительного контроля городских коммуникаций.", "Организационная поддержка - 4");
                break;
            }

            case "Проектировщик городской инфраструктуры": {
                lite(chatId,"Требования: сбор информации и подготовки рекомендаций касательно использования и застройки земельного участка; проведение сбора и анализа данных об экономических, юридических, политических, культурных, демографических социологических, физических и экологических факторах, для наиболее выгодного использования; участие в консультациях по архитектурному планированию городских территорий с привлечением администрации, населения и специалистов; отведение земельных угодий под парки, школы, учреждения, аэропорты, шоссе и иные проекты; Участие в консультациях по архитектурному планированию городских территорий с привлечением администрации, населения и специалистов.Отведение земельных угодий под парки, школы, учреждения, аэропорты, шоссе и иные проекты; проведение сбора и анализа данных об экономических, юридических, политических, культурных, демографических социологических, физических и экологических факторах, для наиболее выгодного использования; сбор информации и подготовки рекомендаций касательно использования и застройки земельного участка.", "Организационная поддержка - 4");
                break;
            }

            case "Проектировщик садово-парковых ансамблей": {
                lite(chatId,"Требования: предоставление услуг по созданию архитектурных проектов, инженерно-технических решений, разработке чертежей; управление проектами строительства, выполнению строительного контроля и авторского надзора; изучение и разработка ландшафтных решений для парковых, лесопарковых, садовых территорий; консультирование по формированию архитектурных решений: анализ и оценка территорий, способы решения затруднений при формировании нового облика парковых территорий; проведение исследовательских работ и усовершенствование методов возведения сложных инженерных сооружений; консультирование заказчиков при строительстве таких сооружений как мосты, дороги, плотины, каналы, трубопроводы, канализация и системы паводкового блока; определение методов строительства, необходимых материалов для соблюдения стандартов качества при ведении строительных работ; организация и контроль технического обслуживания, ремонта существующих сооружений.", "Организационная поддержка - 4");
                break;
            }

            case "Инженер строитель": {
                lite(chatId,"Требования: управление строительными работами при возведении гидросооружений и коммуникаций для парков; осуществление руководства строительством мостов, путепроводов и иных искусственных дорожных сооружений; ведение разработки проектной документации инженерных сооружений; осуществление реконструкции устаревших инженерных сооружений и коммуникаций; оценивание и коррекция строительных проектов с точки зрения их безопасности для окружающей среды;Проектирование систем по очистке окружающей среды; осуществление технической поддержки проектов по защите окружающей среды; взаимодействие со специалистами в рамках охраны окружающей среды.", "Организационная поддержка - 4");
                break;
            }

            case "Инженер по охране окружающей среды": {
                lite(chatId,"Требования: наблюдение за уровнем загрязнения окружающей среды; прогнозирование уровня загрязнений воздуха при рассеивании каких-либо веществ в атмосфере; создание документов обосновывающих нормативы допустимых выбросов и сбросов загрязняющих веществ в окружающую среду; осуществление полевых работ и изысканий в области гидрометеорологии и смежных с ней областях, экспедиционные обследования объектов окружающей среды с целью оценки уровней загрязнения.", "Организационная поддержка - 4");
                break;
            }

            case "Гид": {
                lite(chatId,"Требования: сопровождение туристов на территории объекта; обеспечение соблюдения норм и правил техники безопасности; предоставление необходимой информации по запросам от участников экскурсионной группы; оказание первой помощи пострадавшим в составе экскурсионной группы; проведение тематических экскурсии для посетителей по паркам, заповедникам и садам; подготовка экскурсионного маршрута; организация посадки и отправки экскурсий на транспортных средствах; согласование разрешений на посещения с экскурсиями различных объектов;.", "Организационная поддержка - 4");
                break;
            }

            case "Кассир": {
                lite(chatId,"Требования: ведение отчетности о вырученных средствах; выдача сдачи, билетов и квитанций об оплатеВыдача билетов; управление кассовыми аппаратами, в том числе аппаратами бесконтактной оплаты; продажа билетов; бронирование билеты по предварительной заявке; информирование посетителей о стоимости, наличии либо отсутствии платных услуг, их длительности; проверка наличия билетов;.", "Организационная поддержка - 4");
                break;
            }

            case "Директор ботанического сада": {
                lite(chatId,"Требования: распределение и назначение задач сотрудникам, проверка выполнения текущих работ, обеспечение сотрудников необходимыми ресурсами; планирование и организация повседневную деятельность для сотрудников; контроль персонала, оценка его работы и эффективность деятельности; управление бюджетами, осуществление контроля расходов и обеспечение эффективного использования ресурсов; обеспечение руководства и общего направления деятельности ботанического сада; несение ответственности за сохранение работоспособности и функциональности всех подразделений ботанического сада; участие в создании специальных коллекций растений в целях сохранения и разнообразия и обогащения растительного мира, а также осуществление научной и учебной деятельностиОсуществление программ сохранения и восстановления редких и исчезающих видов растений и животных, в том числе ценных видов в хозяйственном, научном и культурном отношенияхОрганизация мероприятий для популяризации знаний о важности деятельности ботанических садов среди населения, ведение просветительской деятельности.", "Организационная поддержка - 4");
                break;
            }

            case "Директор парка культуры и отдыха": {
                lite(chatId,"Требования: обеспечение всех требований культурно-развлекательной программы; планирование повседневной деятельности организации; контроль персонала и оценка эффективности его работы; управление бюджетами, осуществление контроля расходов и обеспечение эффективного использования ресурсов; распределение и назначение задач сотрудникам, проверку выполнения текущих работ, обеспечение сотрудников необходимыми ресурсами, планирование деятельности организации на несколько шагов вперед; управление деятельностью парка культуры и отдыха; организация условий для поддержки надлежащего вида, инфраструктуры и технической оснащенности парка; обеспечение безопасности посетителям парка;развитие деятельности парка, утверждение проектов культмассовых мероприятий, участие в привлечении средств для финансирования проектов развития парка.", "Организационная поддержка - 4");
                break;
            }

            case "Начальник финансово-экономического отдела": {
                lite(chatId,"Требования: планирование, управление и координация финансовой деятельности предприятия или организации; разработка и управление бюджетом организации, контроль расходов и обеспечение эффективного использования ресурсов; руководство финансовой службой организациями, составление отчетов о расходах  и доходах организации; представление организации на различных форумах и выставках в сфере сельского хозяйства; проведение внутреннего финансового аудита организации; подготовка и оформление налоговой документации организации; осуществление свода и консолидации финансовой отчётности; принятие оперативных решений при финансовом планировании.", "Организационная поддержка - 4");
                break;
            }

            case "Директор по персоналу": {
                lite(chatId,"Требования: составление документов об обязанностях, ответственности и должностных инструкциях сотрудников с привлечением инженера по охране труда, юриста и финансового директораУстановление заработной платы сотрудников, а также консультация их относительно условий труда и занятости; контроль за соблюдением безопасности условий труда сотрудников; подготовка сотрудников к работе над заявленными проектами; планирование и координация деятельности персонала; формирование правил производственных отношений, политики и практики работы предприятия или организации; подбор и трудоустройство персонала; участие в составление правовых документов организации.", "Организационная поддержка - 4");
                break;
            }

            case "Заведующий хозяйством": {
                lite(chatId,"Требования: составление бюджета хозяйственного отдела, выставление счетов на оплату для приобретения необходимого оборудования, расходных материалов и иных вещей; организация деятельности хозяйственных служб: уборку и ремонт территории, контроль объектов; формирование отчётности об имуществе находящемся на балансе организации; контроль и оценка эффективности работы порученного ему персонала; руководство всей хозяйственной деятельности организации и сохранение целостности её имущества; наведением порядка на подведомственной ему территорииУчет и покупка требующихся товаров, организация договоренностей с поставщиками и т.д.; подготовка отчетов о выполненных работах.", "Организационная поддержка - 4");
                break;
            }

            case "Поддержка знаний - 2": {
                var response = new SendMessage(chatId, "Вы выбрали Поддержка знаний - 2");

                response.replyMarkup(new ReplyKeyboardMarkup(
                        new String[] {"Профессор, преподаватель ботаники / биологии, ВУЗ"},
                        new String[] {"Преподаватель биологии, колледж"},
                        new String[] {"Сады, парки, редкие растения и животные"}
                ).resizeKeyboard(true));

                this.execute(response);
                break;
            }

            case "Профессор, преподаватель ботаники / биологии, ВУЗ": {
                lite(chatId,"Требования: разработка и изменение учебных программ; подготовка учебных курсов по преподаваемым предметам в соответствии с требованиями по таким предметам как биология, ботаника и т.д.; руководство экспериментальной и практической работой студентов; проверка и оценка контрольных работ и экзаменов; организация экспериментальных исследований с целью создания новых разработок и получения новых  возможностей использований растений, их сохранения и восстановления природных ресурсов; подготовка высококвалифицированных кадров по всем основным направлениям предметной области по защиты и использования растений в соответствии с потребностями общества и государства; проведение научных исследований, развитие концепций, теорий и методов, связанных с его специальностью, для применения в сфере защиты окружающей среды, эффективного природопользования и т.д.; организация исследований направленные на получение новых знаний о природе, биоразнообразии, растениях их видах и использовании; проведение прикладные исследования с привлечением студентов для достижения конкретных целей и практических задач.", "Поддержка знаний - 2");
                break;
            }

            case "Преподаватель биологии, колледж": {
                lite(chatId,"Требования: подготовка специалистов и служащих среднего звена, обучение их прикладными специальностями, с целью формирования технических специалистов в рамках научной деятельности; организация исследований направленных на получение новых знаний о природе, биоразнообразии, растениях их видах и использовании; проведение прикладных исследований с использованием различного оборудования, обучение работе с оборудованием; организация экспериментальных исследований с целью создания новых разработок и получения новых возможностей использований растений, сохранения и восстановления многообразия живой природы; разработка и изменение учебных программы; подготовка учебные курсы по преподаваемым предметам в соответствии с требованиями по таким предметам как биология, ботаника и т.д.; руководство экспериментальной и практической работой студентов; проведение проверки и оценки контрольных работ и экзаменов; проведение научных исследований и развитие концепции, теорий и методов, связанных с его специальностью.", "Поддержка знаний - 2");
                break;
            }

            case "Хранение": {
                var response = new SendMessage(chatId, "Хранение");

                response.replyMarkup(new ReplyKeyboardMarkup(
                        new String[] {"Красота и мода", "Питание", "Культура - 2"},
                        new String[] {"Финансы", "ЖКХ и инфраструктура", "Воспитание"},
                        new String[] {"Образовательные технологии"}
                ).resizeKeyboard(true));

                this.execute(response);
                break;
            }

            case "Красота и мода": {
                var response = new SendMessage(chatId, "Красота и мода");

                response.replyMarkup(new ReplyKeyboardMarkup(
                        new String[] {"Красота человека", "Красота вокруг человека"},
                        new String[] {"Организационная поддержка - 5", "Информационная поддержка"},
                        new String[] {"Помощь по хозяйству - 3", "Хранение"}
                ).resizeKeyboard(true));

                this.execute(response);
                break;
            }

            case "Красота человека": {
                var response = new SendMessage(chatId, "Красота человека");

                response.replyMarkup(new ReplyKeyboardMarkup(
                        new String[] {"Парикмахер, Барбер, Брадобрей, Специалист по уходу за волосами, Стилист-парикмахер"},
                        new String[] {"Косметологи и работники родственных занятий, Визажист, Гримёр-пастижёр, Косметолог"},
                        new String[] {"Консультант по коррекции фигуры , Лекарь (фитотерапия), Массажист (не медицинский)"},
                        new String[] {"Рабочий-ремесленник, изготовление тканей, Вышивальщица"},
                        new String[] {"Модельер ортопедической обуви, Обувщик по ремонту обуви"},
                        new String[] {"Инструктор по альпинизму и скалолазанию, Инструктор по аэробике"},
                        new String[] {"Модель для съемок в рекламе, Демонстратор причесок, Манекенщик"},
                        new String[] {"Красота и мода"}
                ).resizeKeyboard(true));

                this.execute(response);
                break;
            }

            case "Парикмахер, Барбер, Брадобрей, Специалист по уходу за волосами, Стилист-парикмахер": {
                lite(chatId,"Требования: 1) создание причёсок; 2) окраска и аналогичные работы; 3) бритьё бороды; 4) производство париков 1) создание стрижек; 2) бритьё бороды; 3) уход за кожей головы; 4) подгонка париков.", "Красота человека");
                break;
            }

            case "Косметологи и работники родственных занятий, Визажист, Гримёр-пастижёр, Косметолог": {
                lite(chatId,"Требования: 1) маникюр и педикюр; 2) создание причёсок; 3) нанесение макияжа 4) производство париков 1) уход за кожей; 2) массаж; 3) наложение грима; 4) уход за ногтями", "Красота человека");
                break;
            }

            case "Консультант по коррекции фигуры , Лекарь (фитотерапия), Массажист (не медицинский)": {
                lite(chatId,"Требования: 1) внебольничные оздоровительные процедуры; 2) немедицинские оздоровительные процедуры; 3) производство травяных настоев; 4) производство лекарственных растительных продуктов 1) обсуждение потребностей клиента; 2) немедицинский массаж тела; 3) помощь клиенту с коррекцией фигуры и созданием диеты; 4) лечение народными средствами и методами", "Красота человека");
                break;
            }

            case "Рабочий-ремесленник, изготовление тканей, Вышивальщица": {
                lite(chatId,"Требования: 1) ремонт одежды; 2) создание трикотажных и вязаных предметов одежды 3) пошив одежды на заказ 4) создание аксессуаров одежды. 1) ремонт тканей и одежды 2) снятие швов с одежды 3) подбор нитей 4) вышивка узоров", "Красота человека");
                break;
            }

            case "Модельер ортопедической обуви, Обувщик по ремонту обуви": {
                lite(chatId,"Требования: 1) создание обуви; 2) создание деталей для обуви; 3) создание гетр, краг и аналогичных изделий; 4) создание обуви из текстильных материалов. 1) создание, ремонт и переделка обуви; 2) создание, переделка и ремонт ортопедической обуви; 3) изучение чертежей для изготовления обуви; 4) изучение ярлыков на обуви для получение информации об изделии.", "Красота человека");
                break;
            }

            case "Инструктор по альпинизму и скалолазанию, Инструктор по аэробике": {
                lite(chatId,"Требования: 1)услуги тренеров; 2)спортивное обучение; 3)обучение плаванию; 4)обучение на базах. 1)оценка способностей; 2)обучение движениям; 3)работа с оборудованием; 4)техника безопасности.", "Красота человека");
                break;
            }

            case "Модель для съемок в рекламе, Демонстратор причесок, Манекенщик": {
                lite(chatId,"Требования: 1) владение основами хореографии и этикета 2) владение навыками актёрского мастерства 3) профессиональная демонстрация одежды и аксессуаров 4) знания в сфере моды 1) показ образцов одежды 2) владение приемами показа одежды: хождение, повороты и т.д. 3) позирование в качестве натурщика 4) позирование в качестве модели", "Красота человека");
                break;
            }

            case "Красота вокруг человека": {
                var response = new SendMessage(chatId, "Красота вокруг человека");

                response.replyMarkup(new ReplyKeyboardMarkup(
                        new String[] {"Аппаратчик релаксации и стабилизации пряжи, ткани и полотна, Обработчик влокна и ткани"},
                        new String[] {"Оператор вязально-прошивного оборудования, Ковровщик-оператор, Ткач, Вязальщик, Ковровщик"},
                        new String[] {"Оператор вышивальной машины, Оператор швейного оборудования, Сборщик изделий из кожи и меха"},
                        new String[] {"Аппаратчик водно-химической обработки, Аппаратчик дубления"},
                        new String[] {"Формовщик головных уборов (швейное производство), Шляпник"},
                        new String[] {"Художник-декоратор (средней квалификации). Дизайнер игрушек, Художник по костюмам"},
                        new String[] {"Ландшафтный архитектор , Ландшафтный дизайнер, Декоратор интерьера , Дизайнер интерьера"},
                        new String[] {"Изготовитель художественных изделий из янтаря, Огранщик алмазов и бриллиантов"},
                        new String[] {"Изготовитель изделий из тканей с художественной росписью, Вышивальщик по коже и меху"},
                        new String[] {"Разрисовщик фарфоровых и керамических изделий, Разрисовщик по стеклу, Разрисовщик игрушек"},
                        new String[] {"Изготовитель скульптурно-художественных изделий, Изготовитель художественных изделий из металла"},
                        new String[] {"Красота и мода"}
                ).resizeKeyboard(true));

                this.execute(response);
                break;
            }

            case "Аппаратчик релаксации и стабилизации пряжи, ткани и полотна, Обработчик влокна и ткани": {
                lite(chatId,"Требования: 1) изготовление пряжи и ниток; 2) подготовка шёлка; 3) создание бумажной пряжи; 4) подготовка шерсти. 1) управление машиной для создания волокна; 2) управление машиной для создание распушенной шерсти; 3) управление машиной для смешивания текстильных волокон 4) управление машиной для создание прядей и холстов", "Красота вокруг человека");
                break;
            }

            case "Оператор вязально-прошивного оборудования, Ковровщик-оператор, Ткач, Вязальщик, Ковровщик": {
                lite(chatId,"Требования: 1) создание трикотажных и вязаных предметов одежды; 2) создание чулочно-носочных изделий; 3) создание трикотажных и вязаных тканей; 4) создание широких тканей хлопчатобумажного типа. 1) управление батареями автоматических вязальных машин; 2) продевание пряжи, нити и ткани сквозь иглы и ролики машин; 3) обслуживание автоматических станков; 4) управление работой станков для пряжи и шнуров.", "Красота вокруг человека");
                break;
            }

            case "Оператор вышивальной машины, Оператор швейного оборудования, Сборщик изделий из кожи и меха": {
                lite(chatId,"Требования: 1) создание тюля и других сетчатых тканей; 2) создание этикеток, значков и нашивок; 3) создание верхней одежды; 4) пошив одежды на заказ. 1) использование и обслуживание швейных машин; 2) прикрепление пуговиц, крючков, молний и других аксессуаров; 3) обслуживание полуавтоматических швейных машин; 4) использование оверлоков и плосковязальных машин.", "Красота вокруг человека");
                break;
            }

            case "Аппаратчик водно-химической обработки, Аппаратчик дубления": {
                lite(chatId,"Требования: 1) дубление, крашение и выделка шкур и кожи; 2) создание разных типов кожи; 3) создание составной кожи; 4) мездрение, стрижка, щипка, выделка, дубление и отбелка шкур; 1) управление и контроль работы машин для удаления плоти и жира со шкур; 2) управление и контроль работы машин для удаления волос со шкурок; 3) управление и контроль работы машин по удалению корней волос; 4) управление и контроль работы машин для уменьшения толщины шкур", "Красота вокруг человека");
                break;
            }

            case "Формовщик головных уборов (швейное производство), Шляпник": {
                lite(chatId,"Требования: 1) создание головных уборов из меха; 2) ремонт одежды; 3) создание аксессуаров одежды 4) пошив одежды на заказ. 1) управление и контроль за работой машин для формирования и изготовления шляп 2) ремонт одежды 3) подбор нитей 4) вышивка узоро", "Красота вокруг человека");
                break;
            }

            case "Художник-декоратор (средней квалификации). Дизайнер игрушек, Художник по костюмам": {
                lite(chatId,"Требования: 1) создание начального проекта; 2) разработка изделия или продукта; 3) работа в графическом редакторе; 4) дизайн интерьера 1) обсуждение с клиентом проекта; 2) разработка проекта; 3) согласование проекта с клиентом; 4) создание наглядных материалов для демонстрации проекта", "Красота вокруг человека");
                break;
            }

            case "Ландшафтный архитектор , Ландшафтный дизайнер, Декоратор интерьера , Дизайнер интерьера": {
                lite(chatId,"Требования: 1) создание начального проекта; 2) разработка изделия или продукта; 3) работа в графическом редакторе; 4) дизайн интерьера. 1) обсуждение с клиентом проекта; 2) изучение условий и требований к работе; 3) разработка проекта; 4) подбор материалов и изделий для работы.", "Красота вокруг человека");
                break;
            }

            case "Изготовитель художественных изделий из янтаря, Огранщик алмазов и бриллиантов": {
                lite(chatId,"Требования: 1) производство жемчуга; 2) обработка драгоценных и полудрагоценных камней; 3) огранка алмазов; 4) производство ювелирных изделий. 1) отливка изделий вручную; 2) создание дизайна украшений; 3) создание рисунка на изделиях; 4) ремонт украшений", "Красота вокруг человека");
                break;
            }

            case "Изготовитель изделий из тканей с художественной росписью, Вышивальщик по коже и меху": {
                lite(chatId,"Требования: 1) производство трикотажной и вязанной одежды; 2) производство чулочно-носочных изделий; 3) производство трикотажных и вязаных тканей; 4) производство широких тканей хлопчатобумажного и шерстяного типа 1) прядение и окраска натуральными красками тканей; 2) ткачество, вязание, вышивание, кружевоплетение; 3) подготовка и окраска шкур и кожи; 4) изготовлений изделий в технике батика", "Красота вокруг человека");
                break;
            }

            case "Разрисовщик фарфоровых и керамических изделий, Разрисовщик по стеклу, Разрисовщик игрушек": {
                lite(chatId,"Требования: 1) гравировка изделий из благородных и неблагородных металлов; 2) деятельность скульпторов, художников, гравировщиков; 3) создание и размещение уличной рекламы; 4) участие в создании театральных представлений. 1) нанесение узоров на изделия; 2) перенос рисунков с помощью трафаретов; 3) создание эскизов и заготовок; 4) передача с помощью рисунков идей, эмоций и настроения.", "Красота вокруг человека");
                break;
            }

            case "Изготовитель скульптурно-художественных изделий, Изготовитель художественных изделий из металла": {
                lite(chatId,"Требования: 1) гравировка предметов из благородных или неблагородных металлов; 2) деятельность скульпторов, художников, гравировщиков; 3) создание керамических игрушек; 4) создание игр и игрушек 1) создание художественных изделий из металла; 2) монтаж металлических изделий; 3) патинирование и тонирование скульптур; 4) отливка, зачистка, скульптурная обработка", "Красота вокруг человека");
                break;
            }

            case "Организационная поддержка - 5": {
                var response = new SendMessage(chatId, "Организационная поддержка - 5");

                response.replyMarkup(new ReplyKeyboardMarkup(
                        new String[] {"Бригадир в супермаркете, Директор магазина"},
                        new String[] {"Агент по связям с общественностью, Маркетолог"},
                        new String[] {"Начальник финансово-экономического отдела, Руководитель по осуществлению финансового контроля"},
                        new String[] {"Архитектор информационных систем, Сетевой администратор, Системный администратор, IT-проповедник"},
                        new String[] {"Секретарь широкого профиля, Личный помощник, Секретарь кабинета, Стенографист"},
                        new String[] {"Администратор фронт-офиса, Бортпроводница (на земле), Специалист Call-центра"},
                        new String[] {"Руководитель по осуществлению финансового контроля, Оператор кросс-логистики"},
                        new String[] {"Красота и мода"}
                ).resizeKeyboard(true));

                this.execute(response);
                break;
            }

            case "Бригадир в супермаркете, Директор магазина": {
                lite(chatId,"Требования: 1) планирование; 2) контроль и управление компаниями; 3) составление бюджета; 4) розничная торговля. 1) составление графиков и планов работы; 2) обучение сотрудников; 3) обслуживание клиентов; 4) работа с персоналом.", "Организационная поддержка - 5");
                break;
            }

            case "Агент по связям с общественностью, Маркетолог": {
                lite(chatId,"Требования: 1) связь с общественностью и коммуникация; 2) лоббистская деятельность; 3) создание и размещение рекламы; 4) проведение маркетинговых кампаний. 1) планирование и организация рекламных кампаний; 2) консультирование клиентов и руководства по вопросам маркетинга; 3) написание текстов и сценариев для рекламы; 4) сбор и обработка информации и предпочтениях потребителей.", "Организационная поддержка - 5");
                break;
            }

            case "Начальник финансово-экономического отдела, Руководитель по осуществлению финансового контроля": {
                lite(chatId,"Требования: 1)оперативный контроль работы предприятия; 2)анализ счетов; 3)оформление сделок; 4)подготовка деклараций 1)управление организацией; 2)контроль бюджета; 3)подбор персонала; 4)взаимодействие с учреждениями", "Организационная поддержка - 5");
                break;
            }

            case "Архитектор информационных систем, Сетевой администратор, Системный администратор, IT-проповедник": {
                lite(chatId,"Требования: 1) разработка компьютерных систем; 2) управление данными; 3)техническая поддержка; 4)настройка компьютеров 1)администрирование сетей; 2)диагностика проблем; 3)аварийное восстановление; 4)работа с программным обеспечением", "Организационная поддержка - 5");
                break;
            }

            case "Секретарь широкого профиля, Личный помощник, Секретарь кабинета, Стенографист": {
                lite(chatId,"Требования: 1)подготовка документов; 2)обработка текста; 3)редактирование документов; 4)секретарские услуги. 1)печать документов; 2)работа с программами; 3)хранение документов; 4)работа с почтой.", "Организационная поддержка - 5");
                break;
            }

            case "Администратор фронт-офиса, Бортпроводница (на земле), Специалист Call-центра": {
                lite(chatId,"Требования: 1)подготовка документов; 2)обработка текста; 3)редактирование документов; 4)секретарские услуги. 1)регистрация клиентов; 2)информирование; 3)работа с жалобами; 4) прием платежей.", "Организационная поддержка - 5");
                break;
            }

            case "Руководитель по осуществлению финансового контроля, Оператор кросс-логистики": {
                lite(chatId,"Требования: 1)представление интересов сторон; 2)рассмотрение дел; 3)подготовка документов; 4)юридическая работа 1)проведение слушаний; 2) определение прав и обязанностей сторон; 3)вынесение решений; 4)объявление приговора.", "Организационная поддержка - 5");
                break;
            }

            case "Информационная поддержка": {
                var response = new SendMessage(chatId, "Информационная поддержка");

                response.replyMarkup(new ReplyKeyboardMarkup(
                        new String[] {"Журналист, ведущий светскую хронику, Газетный репортёр, Корреспондент"},
                        new String[] {"Ассистент художника по комбинированным съемкам, Звукорежиссер Кинорежиссёр"},
                        new String[] {"Красота и мода"}
                ).resizeKeyboard(true));

                this.execute(response);
                break;
            }

            case "Журналист, ведущий светскую хронику, Газетный репортёр, Корреспондент": {
                lite(chatId,"Требования: 1) создание фоторепортажей; 2) проведение независимых расследований; 3) деятельность ассоциаций журналистов; 4) создание видеоматериалов, телевизионных программ. 1) получение информации и её проверка; 2) подбор материалов для публикации; 3) проверка и редактирование текста 4) подбор рекламных материалов", "Информационная поддержка");
                break;
            }

            case "Ассистент художника по комбинированным съемкам, Звукорежиссер Кинорежиссёр": {
                lite(chatId,"Требования: 1) производство видеоматериалов; 2) монтаж, редактирование, дубляж; 3) деятельность самостоятельных актёров и режиссёров; 4) создание телевизионных программ. 1) руководство и режиссура 2) контроль и организация этапов подготовки видеоматериалов 3) найм и контроль технического персонала 4) ведение архивов производства", "Информационная поддержка");
                break;
            }

            case "Помощь по хозяйству - 3": {
                var response = new SendMessage(chatId, "Помощь по хозяйству - 3");

                response.replyMarkup(new ReplyKeyboardMarkup(
                        new String[] {"Бухгалтер по налогообложению, Бухгалтер-ревизор, Координатор по бюджету"},
                        new String[] {"Продавец, продавец-консультант"},
                        new String[] {"Оператор чистильной машины, Чистильщик ткани, изделий, Гладильщица, Прачка, Оператор стиральных машин"},
                        new String[] {"Сотрудник службы внутренней безопасности, Охранник, Инкассатор"},
                        new String[] {"Красота и мода"}
                ).resizeKeyboard(true));

                this.execute(response);
                break;
            }

            case "Бухгалтер по налогообложению, Бухгалтер-ревизор, Координатор по бюджету": {
                lite(chatId,"Требования: 1)подготовка счетов; 2)анализ счетов; 3)подготовка деклараций; 4)консультации 1)бухгалтерский контроль; 2)подготовка финансовых отчетов; 3)отчёты по доходам; 4)консультирование руководства.", "Помощь по хозяйству - 3");
                break;
            }

            case "Продавец, продавец-консультант": {
                lite(chatId,"Требования: торговля сувенирами и предметами; торговля книгами; торговля предметами художественного творчества; ответы на вопросы об использовании товаров; ответы на вопросы об услугах, оказываемых организацией; получение оплаты, выдача чеков, счетов-фактур и т.д; проведение инвентаризации и прием заказов на товары; укладка и размещение товаров для продажи.", "Помощь по хозяйству - 3");
                break;
            }

            case "Оператор чистильной машины, Чистильщик ткани, изделий, Гладильщица, Прачка, Оператор стиральных машин": {
                lite(chatId,"Требования: 1) отбеливание и окрашивание волокон, пряжи, тканей и изделий; 2) сушка, обработка паром, декатировка, штопка; 3) отбеливание джинсовых изделий; 4) плиссировка и аналогичная обработка тканей. 1) использование и контроль оборудования для обработки тканей и т.п.; 2) обслуживание машин для усаживания тканей; 3) обслуживание автоматических машин для вычёсывания и вычищения меха; 4) наблюдение за показаниями панелей и индикаторов оборудования.", "Помощь по хозяйству - 3");
                break;
            }

            case "Сотрудник службы внутренней безопасности, Охранник, Инкассатор": {
                lite(chatId,"Требования: 1) услуги телохранителей 2) охрана и патрулирование 3) обеспечение безопасности; 4) инкассация и доставка денег 1)патрулирование помещений; 2)выдача пропусков; 3)сохранение порядка; 4)предотвращение пожаров.", "Помощь по хозяйству - 3");
                break;
            }

            case "Питание": {
                var response = new SendMessage(chatId, "Питание");

                response.replyMarkup(new ReplyKeyboardMarkup(
                        new String[] {"Производство продуктов"},
                        new String[] {"Организационная поддержка - 6"},
                        new String[] {"Приготовление еды"},
                        new String[] {"Помощь по хозяйству - 4"},
                        new String[] {"Хранение"}
                ).resizeKeyboard(true));

                this.execute(response);
                break;
            }

            case "Производство продуктов": {
                var response = new SendMessage(chatId, "Производство продуктов");

                response.replyMarkup(new ReplyKeyboardMarkup(
                        new String[] {"Изготовитель мясных полуфабрикатов, Забойщик скота, Обработчик мясных туш"},
                        new String[] {"Изготовитель шоколада, Глазировщик, Кондитер, Конфетчик, Пекарь (общий профиль)"},
                        new String[] {"Изготовитель мороженого,Изготовитель кумыса, Изготовитель творога, Сыродел"},
                        new String[] {"Изготовитель пищевых приправ, Засольщик овощей, Изготовитель варенья"},
                        new String[] {"Сушильщик табака, Содовщик, Изготовитель сигар"},
                        new String[] {"Обработчик винного сырья"},
                        new String[] {"Аппаратчик стерилизации консервов, Оператор автомата по производству вареных колбас"},
                        new String[] {"Аппаратчик производства плавленого сыра, Аппаратчик пастеризации"},
                        new String[] {"Аппаратчик обработки зерна, Мельник специй, Оператор пульта управления элеватором"},
                        new String[] {"Машинист тестомесильных машин, Оператор хлебопечки, Оператор машины по изготовлению бисквитов"},
                        new String[] {"Аппаратчик сульфитации овощей и фруктов, Аппаратчик рафинации жиров и масел"},
                        new String[] {"Аппаратчик обработки рафинадных голов, Аппаратчик варки утфеля"},
                        new String[] {"Аппаратчик обработки эфирных масел, Аппаратчик процесса брожения Варщик сиропов, соков"},
                        new String[] {"Машинист дробильных машин (табак), Машинист линии непрерывной ферментации табака"},
                        new String[] {"Мастер хлебопекарни"},
                        new String[] {"Квалифицированный сельскохозяйственный рабочий (смешанное производство), Сити-фермер"},
                        new String[] {"Квалифицированный сельскохозяйственный рабочий, рыбоводство, Рыбовод, Фермер, Сити-фермер"},
                        new String[] {"Рыбак во внутренних водоемах, Рыбак морской зоны, Рыбак прибрежного лова"},
                        new String[] {"Оператор рыбообрабатывающего оборудования, Рыбак глубоководной зоны"},
                        new String[] {"Охотник (беркутши), Охотник-траппер (капканщик), Охотник промысловый)"},
                        new String[] {"Фермер, производящий продукцию для личного потребления, Охотник (личное потребление), Сити-фермер"},
                        new String[] {"Неквалифицированный рабочий рыболовства (рыбоводства и охотничьего хозяйства), Пастух"},
                        new String[] {"Питание"}
                ).resizeKeyboard(true));

                this.execute(response);
                break;
            }

            case "Изготовитель мясных полуфабрикатов, Забойщик скота, Обработчик мясных туш": {
                lite(chatId,"Требования: 1)забой скота; 2)обрезка туш; 3)изготовление колбас; 4)продажа мяса и рыбы 1)упаковывание мяса; 2)производство мяса в тушах; 3)производство мяса в порциях; 4)производство копченого мяса", "Производство продуктов");
                break;
            }

            case "Изготовитель шоколада, Глазировщик, Кондитер, Конфетчик, Пекарь (общий профиль)": {
                lite(chatId,"Требования: 1)изготовление мучных изделий; 2) соединение ингредиентов; 3)проверка качества сырья; 4) контроль температуры печей 1) производство какао; 2) производство шоколада; 3) производство сахарных кондитерских изделий; 4)производство леденцов.", "Производство продуктов");
                break;
            }

            case "Изготовитель мороженого,Изготовитель кумыса, Изготовитель творога, Сыродел": {
                lite(chatId,"Требования: 1)кипячение молока; 2)взбивание сливок в масло; 3) введение закваски в молоко; 4) контроль качества продукции. 1) производство сыра и творога; 2) производство сыворотки; 3) производство лактозы; 4) производство мороженого", "Производство продуктов");
                break;
            }

            case "Изготовитель пищевых приправ, Засольщик овощей, Изготовитель варенья": {
                lite(chatId,"Требования: 1) изготовление сока из фруктов; 2) извлечение масел из семян; 3) сушка фруктов; 4) перекладывание продуктов в банки. 1) производство продуктов из фруктов; 2) консервирование фруктов; 3) производство продуктов из фруктов или овощей; 4) производство продуктов и паст из орехов.", "Производство продуктов");
                break;
            }

            case "Сушильщик табака, Содовщик, Изготовитель сигар": {
                lite(chatId,"Требования: 1) изготовление напитков; 2) изготовление табачной продукции; 3)проверка качества; 4)обработка табака", "Производство продуктов");
                break;
            }

            case "Обработчик винного сырья": {
                lite(chatId,"Требования: 1) производство игристого вина; 2) производство вина из виноградного сусла; 3) производство алкогольных напитков; 4) производство ароматизированных вин.", "Производство продуктов");
                break;
            }

            case "Аппаратчик стерилизации консервов, Оператор автомата по производству вареных колбас": {
                lite(chatId,"Требования: 1) термическая обработка мяса; 2) копчение рыбы; 3) замораживание рыбы; 4) производство вареных колбас 1) производство мяса в порциях; 2) производство сушеного мяса; 3) производство мясных продуктов; 4) производство продуктов из рыбы.", "Производство продуктов");
                break;
            }

            case "Аппаратчик производства плавленого сыра, Аппаратчик пастеризации": {
                lite(chatId,"Требования: 1) пастеризация молока; 2) изготовление заквасок; 3) производство кисломолочных продуктов; 4) изготовление шоколада 1) производство кондитерских изделий; 2) производство молока; 3) производство сливок; 4) производство сливочного масла.", "Производство продуктов");
                break;
            }

            case "Аппаратчик обработки зерна, Мельник специй, Оператор пульта управления элеватором": {
                lite(chatId,"Требования: 1) подготовка зерна к помолу; 2) очистка зерна; 3) подготовка зерна к переработке; 4) управление работой машин 1) обработка зерна; 2) обработка риса; 3) обработка овощей; 4) производство готовых завтраков.", "Производство продуктов");
                break;
            }

            case "Машинист тестомесильных машин, Оператор хлебопечки, Оператор машины по изготовлению бисквитов": {
                lite(chatId,"Требования: 1) обслуживание машин для разделки хлеба; 2) формование хлебных палочек; 3) приготовление макаронных изделий; 4) резка и формовка теста 1) производство хлеба и булочек; 2) производство пирожных; 3) производство сухарей; 4) производство мучных кондитерских изделий", "Производство продуктов");
                break;
            }

            case "Аппаратчик сульфитации овощей и фруктов, Аппаратчик рафинации жиров и масел": {
                lite(chatId,"Требования: 1) отжим соков; 2) варка сиропов; 3) чистка картофеля; 4) наполнение банок 1) производство продуктов из фруктов; 2) консервирование фруктов; 3) производство продуктов из и овощей; 4) производство паст из орехов.", "Производство продуктов");
                break;
            }

            case "Аппаратчик обработки рафинадных голов, Аппаратчик варки утфеля": {
                lite(chatId,"Требования: 1) регулирование подачи патоки 2) управление производственными процессами; 3) переключение аппаратов; 4) устранение неисправностей в работе приборов 1) рафинирование сахара; 2) производство сахарных сиропов; 3) производство черной патоки; 4) производство кленового сиропа.", "Производство продуктов");
                break;
            }

            case "Аппаратчик обработки эфирных масел, Аппаратчик процесса брожения Варщик сиропов, соков": {
                lite(chatId,"Требования: 1) фиксация чайного листа; 2) сушка скрученного чайного листа; 3) спиртовое брожение; 4) обжаривание кофе 1) производство фруктовых и овощных соков; 2) производство безалкогольных напитков; 3) производство минеральной воды; 4) производство лимонада.", "Производство продуктов");
                break;
            }

            case "Машинист дробильных машин (табак), Машинист линии непрерывной ферментации табака": {
                lite(chatId,"Требования: 1) обработки табака; 2) ферментация табака; 3) резка листового табака; 4) укладка в пачки сигарет 1) производство табачных изделий; 2) производство восстановленного табака; 3) очистка от стеблей; 4) повторная сушка табака.", "Производство продуктов");
                break;
            }

            case "Мастер хлебопекарни": {
                lite(chatId,"Требования: 1) определение последовательности работ; 2) определение материальной потребности; 3) распределение обязанностей между рабочими; 4) предоставление отчетов. 1) производство хлеба и булочек; 2) производство пирожных; 3) производство сухарей; 4) производство мучных кондитерских изделий.", "Производство продуктов");
                break;
            }

            case "Квалифицированный сельскохозяйственный рабочий (смешанное производство), Сити-фермер": {
                lite(chatId,"Требования: 1)закупка семян; 2)подготовка земли; 3)закупка кормов; 4) уход за животными 1) подготовка полей; 2) предоставление сельскохозяйственных машин; 3) поддержание земельных угодий; 4)разведение животных.", "Производство продуктов");
                break;
            }

            case "Квалифицированный сельскохозяйственный рабочий, рыбоводство, Рыбовод, Фермер, Сити-фермер": {
                lite(chatId,"Требования: 1)разведение рабы; 2) регистрация данных о росте организмов; 3) контроль поголовья; 4) наблюдение за окружающей средой. 1) разведение морских декоративных рыб; 2) разведение молоди двустворчатых моллюсков; 3) выращивание красной водоросли; 4) культивирование ракообразных.", "Производство продуктов");
                break;
            }

            case "Рыбак во внутренних водоемах, Рыбак морской зоны, Рыбак прибрежного лова": {
                lite(chatId,"Требования: 1) ремонт сетей; 2) выбор рыбопромысловых районов; 3) управление рыболовецкими судами; 4) вытягивание рыболовецких снастей. 1) рыболовство в коммерческих целях; 2) добыча моллюсков; 3) добыча пресноводных животных; 4) сбор пресноводных материалов.", "Производство продуктов");
                break;
            }

            case "Оператор рыбообрабатывающего оборудования, Рыбак глубоководной зоны": {
                lite(chatId,"Требования: 1) управление рыболовецкими судами; 2) прокладывание курсов; 3) эксплуатация навигационных приборов; 4) надзор за экипажем 1) рыболовство в океане и прибрежных водах; 2) добыча морских ракообразных и моллюсков; 3) добыча морских животных; 4) переработка и консервирование рыбы", "Производство продуктов");
                break;
            }

            case "Охотник (беркутши), Охотник-траппер (капканщик), Охотник промысловый)": {
                lite(chatId,"Требования: 1) установка капканов; 2) убой пойманных животных; 3) продажа пойманных животных; 4)ремонт оборудования 1) охота и ловля; 2) отбор животных; 3) производство пушнины; 4) наземный отлов морских млекопитающих.", "Производство продуктов");
                break;
            }

            case "Фермер, производящий продукцию для личного потребления, Охотник (личное потребление), Сити-фермер": {
                lite(chatId,"Требования: 1) подготовка почвы для посадки; 2) выращивание и сбор урожая; 3) уход за домашним скотом; 4) разведение и уход за пчелами 1) подготовка полей; 2)предоставление сельскохозяйственных машин; 3) поддержание земельных угодий; 4)разведение животных", "Производство продуктов");
                break;
            }

            case "Неквалифицированный рабочий рыболовства (рыбоводства и охотничьего хозяйства), Пастух": {
                lite(chatId,"Требования: 1) прополка посевов; 2) ручная посадка и сбор риса; 3)кормление и чистка животных; 4) выпас и доение животных 1)обследование состояния стада; 2)стрижка овец; 3) содержание животных; 4) уборка урожая.", "Производство продуктов");
                break;
            }

            case "Организационная поддержка - 6": {
                var response = new SendMessage(chatId, "Организационная поддержка - 6");

                response.replyMarkup(new ReplyKeyboardMarkup(
                        new String[] {"Администратор фронт-офиса, Бортпроводница (на земле), Ресепшионист гостиницы"},
                        new String[] {"Патентовед, Судебный исполнитель, частный, Юрисконсульт, Сетевой юрист, Виртуальный адвокат"},
                        new String[] {"Личный помощник, Секретарь, Стенографист"},
                        new String[] {"Начальник финансово-экономического отдела, Главный аудитор, Главный бухгалтер"},
                        new String[] {"Главный агроном, Главный мелиоратор, Главный охотовед, Заведующий отделом (в рыбном хозяйстве)"},
                        new String[] {"Координатор производств в распределенных сообществах, Руководитель службы маркетинга"},
                        new String[] {"Продавец продовольственных товаров, Продавец на рынке, Киоскер, Менеджер по управлению онлайн-продажами"},
                        new String[] {"Начальник службы оптовой и розничной торговли, Заведующий отделом в торговле"},
                        new String[] {"Менеджер по управлению онлайн-продажами, Бригадир в супермаркете, Помощник продавца магазина"},
                        new String[] {"Директор (управляющий) ресторана, Заведующий залом (ресторана, кафе и др.), Директор кафе"},
                        new String[] {"Директор гостиницы (гостиничного учреждения), Директор по кейтерингу, Трактирщик"},
                        new String[] {"Питание"}
                ).resizeKeyboard(true));

                this.execute(response);
                break;
            }

            case "Администратор фронт-офиса, Бортпроводница (на земле), Ресепшионист гостиницы": {
                lite(chatId,"Требования: 1)регистрация клиентов; 2)информирование; 3)работа с жалобами; 4) прием платежей 1)подготовка документов; 2)обработка текста; 3)редактирование документов; 4)секретарские услуги", "Организационная поддержка - 6");
                break;
            }

            case "Патентовед, Судебный исполнитель, частный, Юрисконсульт, Сетевой юрист, Виртуальный адвокат": {
                lite(chatId,"Требования: 1)проведение слушаний; 2) определение прав и обязанностей сторон; 3)вынесение решений; 4)объявление приговора 1)представление интересов сторон; 2)рассмотрение дел; 3)подготовка документов; 4)юридическая работа", "Организационная поддержка - 6");
                break;
            }

            case "Личный помощник, Секретарь, Стенографист": {
                lite(chatId,"Требования: 1)печать документов; 2)работа с программами; 3)хранение документов; 4)работа с почтой 1)подготовка документов; 2)обработка текста; 3)редактирование документов; 4)секретарские услуги", "Организационная поддержка - 6");
                break;
            }

            case "Начальник финансово-экономического отдела, Главный аудитор, Главный бухгалтер": {
                lite(chatId,"Требования: 1)управление организацией; 2)контроль бюджета; 3)подбор персонала; 4)взаимодействие с учреждениями. 1)оперативный контроль; 2)анализ счетов; 3)оформление сделок; 4)подготовка деклараций", "Организационная поддержка - 6");
                break;
            }

            case "Главный агроном, Главный мелиоратор, Главный охотовед, Заведующий отделом (в рыбном хозяйстве)": {
                lite(chatId,"Требования: 1) планирование производства; 2) управление бюджетом; 3) переговоры с покупателями; 4) заключение контрактов с фермерами. 1)управление организацией; 2)организация деятельности; 3)принятие рещений; 4)оперативный контроль.", "Организационная поддержка - 6");
                break;
            }

            case "Координатор производств в распределенных сообществах, Руководитель службы маркетинга": {
                lite(chatId,"Требования: 1) организация специальных продаж; 2) составление прайс-листов 3)руководство продажами; 4)руководство персоналом 1)управление организацией; 2)организация деятельности; 3)принятие рещений; 4)оперативный контроль.", "Организационная поддержка - 6");
                break;
            }

            case "Продавец продовольственных товаров, Продавец на рынке, Киоскер, Менеджер по управлению онлайн-продажами": {
                lite(chatId,"Требования: 1) определение ассортимента товаров; 2) получение товаров; 3) разгрузка корзин; 4) получение оплаты на месте 1) розничная торговля пищевыми продуктами; 2) розничная торговля прочими товарами; 3) розничная торговля текстилем; 4) передвижные торговые тележки.", "Организационная поддержка - 6");
                break;
            }

            case "Начальник службы оптовой и розничной торговли, Заведующий отделом в торговле": {
                lite(chatId,"Требования: 1) определение ассортимента продукции, 2) определение цен; 3) реклама товаров заведения; 4) учета запасов и финансов )управление организацией; 2)организация деятельности; 3)принятие рещений; 4)оперативный контроль", "Организационная поддержка - 6");
                break;
            }

            case "Менеджер по управлению онлайн-продажами, Бригадир в супермаркете, Помощник продавца магазина": {
                lite(chatId,"Требования: 1) составление графиков работы 2) инструктирование персонала 3) быстрое обслуживание клиентов 4) размещение товаров для продажи. 1)розничная торговля продуктами; 2) розничная торговля напитками; 3) розничная торговля табачными изделиями; 4) розничная торговля кондитерскими изделиями", "Организационная поддержка - 6");
                break;
            }

            case "Директор (управляющий) ресторана, Заведующий залом (ресторана, кафе и др.), Директор кафе": {
                lite(chatId,"Требования: 1)планирование меню; 2)организация питания; 3)закупка продуктов; 4)соблюдение санитарных норм 1)управление заведением; 2)организация деятельности; 3)принятие рещений; 4)оперативный контроль", "Организационная поддержка - 6");
                break;
            }

            case "Директор гостиницы (гостиничного учреждения), Директор по кейтерингу, Трактирщик": {
                lite(chatId,"Требования: 1)руководство ведением хозяйства; 2) надзор за мерами безопасности; 3)управление барами и ресторанами; 4)соблюдение законов. 1)управление заведением; 2)организация деятельности; 3)принятие рещений; 4)оперативный контроль", "Организационная поддержка - 6");
                break;
            }

            case "Приготовление еды": {
                var response = new SendMessage(chatId, "Приготовление еды");

                response.replyMarkup(new ReplyKeyboardMarkup(
                        new String[] {"Титестер, Сортировщик в производстве пищевой продукции, Дегустатор вина, Дегустатор, Сомелье"},
                        new String[] {"Заготовщик гамбургеров, Изготовитель пиццы, Кухонный рабочий"},
                        new String[] {"Бармен, Бариста, Буфетчик, Официант"},
                        new String[] {"Шеф-повар по солениям, Шеф-повар по соусам, Су-шеф, Шеф-кондитер, Шеф-повар, Бренд-шеф"},
                        new String[] {"Повар в школе, Повар, лечебное учреждение, Повар на банкете, Повар в учреждении, Соусье"},
                        new String[] {"Питание"}
                ).resizeKeyboard(true));

                this.execute(response);
                break;
            }

            case "Титестер, Сортировщик в производстве пищевой продукции, Дегустатор вина, Дегустатор, Сомелье": {
                lite(chatId,"Требования: 1)дегустация напитков; 2) определение качества продуктов; 3) отбраковка некачественных продуктов; 4) фиксирование сорта на ярлыках. 1) розлив в бутылки; 2) упаковывание твердых продуктов; 3) упаковывание жидких продуктов; 4) упаковывание в пакеты.", "Приготовление еды");
                break;
            }

            case "Заготовщик гамбургеров, Изготовитель пиццы, Кухонный рабочий": {
                lite(chatId,"Требования: 1)приготовление простых блюд; 2)нарезка продуктов; 3)принятие заказов; 4)приём ингредиентов. 1)предоставление питания; 2)доставка еды; 3)общественное питание; 4)работа столовых и буфетов.", "Приготовление еды");
                break;
            }

            case "Бармен, Бариста, Буфетчик, Официант": {
                lite(chatId,"Требования: 1)приготовление напитков; 2)накрытие столов; 3)подача меню; 4)прием оплаты 1)работа ресторанов; 2)быстрое обслуживание; 3)подача напитков; 4)работа заведений с подачей напитков.", "Приготовление еды");
                break;
            }

            case "Шеф-повар по солениям, Шеф-повар по соусам, Су-шеф, Шеф-кондитер, Шеф-повар, Бренд-шеф": {
                lite(chatId,"Требования: 1)разработка меню; 2) контроль качества блюд; 3)приготовление пищи; 4)руководство работниками 1)работа ресторанов; 2)предоставление питания; 3)быстрое обслуживание; 4)выездное обслуживание", "Приготовление еды");
                break;
            }

            case "Повар в школе, Повар, лечебное учреждение, Повар на банкете, Повар в учреждении, Соусье": {
                lite(chatId,"Требования: 1)планирование меню; 2)руководство помощниками; 3)смешивание ингредиентов; 4)уборка кухни. 1)работа ресторанов; 2)предоставление питания; 3)быстрое обслуживание; 4)выездное обслуживание", "Приготовление еды");
                break;
            }

            case "Помощь по хозяйству - 4": {
                var response = new SendMessage(chatId, "Помощь по хозяйству - 4");

                response.replyMarkup(new ReplyKeyboardMarkup(
                        new String[] {"Сотрудник службы внутренней безопасности, Инкассатор"},
                        new String[] {"Архитектор информационных систем, Сетевой администратор, Системный администратор"},
                        new String[] {"Бухгалтер по налогообложению, Бухгалтер, Аудитор, Бухгалтер-ревизор, Координатор по бюджету"},
                        new String[] {"Оператор кросс-логистики, Грузчик, Сортировщик багажа, Носильщик на складе, Комплектовщик товаров"},
                        new String[] {"Питание"}
                ).resizeKeyboard(true));

                this.execute(response);
                break;
            }

            case "Сотрудник службы внутренней безопасности, Инкассатор": {
                lite(chatId,"Требования: 1)патрулирование помещений; 2)выдача пропусков; 3)сохранение порядка; 4)предотвращение пожаров 1) услуги телохранителей; 2) охрана и патрулирование; 3) обеспечение безопасности; 4) инкассация и доставка денег", "Помощь по хозяйству - 4");
                break;
            }

            case "Архитектор информационных систем, Сетевой администратор, Системный администратор": {
                lite(chatId,"Требования: 1) администрирование сетей; 2) диагностика проблем; 3) аварийное восстановление; 4)работа с программным обеспечением 1)разработка компьютерных систем; 2)управление данными; 3)техническая поддержка; 4)настройка компьютеров", "Помощь по хозяйству - 4");
                break;
            }

            case "Бухгалтер по налогообложению, Бухгалтер, Аудитор, Бухгалтер-ревизор, Координатор по бюджету": {
                lite(chatId,"Требования: 1)бухгалтерский контроль; 2)подготовка финансовых отчетов; 3)отчёты по доходам; 4)консультирование руководства. 1)подготовка счетов; 2)анализ счетов; 3)подготовка деклараций; 4)консультации.", "Помощь по хозяйству - 4");
                break;
            }

            case "Оператор кросс-логистики, Грузчик, Сортировщик багажа, Носильщик на складе, Комплектовщик товаров": {
                lite(chatId,"Требования: 1)упаковка мебели; 2)перенос товаров; 3)погрузка и разгрузка; 4) складирование товаров 1)услуги носильщиков; 2)перевозка; 3)работа терминалов; 4)работа на железных дорогах", "Помощь по хозяйству - 4");
                break;
            }

            case "Культура - 2": {
                var response = new SendMessage(chatId, "Культура - 2");

                response.replyMarkup(new ReplyKeyboardMarkup(
                        new String[] {"Осмысление - 2"},
                        new String[] {"Производство - 2"},
                        new String[] {"Сохранение - 2"},
                        new String[] {"Хранение"}
                ).resizeKeyboard(true));

                this.execute(response);
                break;
            }

            case "Осмысление - 2": {
                var response = new SendMessage(chatId, "Осмысление - 2");

                response.replyMarkup(new ReplyKeyboardMarkup(
                        new String[] {"Специалист по генеалогии, Историк, Философ, Политолог"},
                        new String[] {"Культура - 2"}
                ).resizeKeyboard(true));

                this.execute(response);
                break;
            }

            case "Специалист по генеалогии, Историк, Философ, Политолог": {
                lite(chatId,"Требования: 1) развитие философских теорий; 2) научные исследования; 3) описание истории; 4) описание природы человеческого опыта; 1)научные разработки; 2) разработки в области гуманитарных наук; 3) междисциплинарные исследования; 4)разработки в области общественных наук.", "Осмысление - 2");
                break;
            }

            case "Производство - 2": {
                var response = new SendMessage(chatId, "Производство - 2");

                response.replyMarkup(new ReplyKeyboardMarkup(
                        new String[] {"Актёр, Артист драмы, Артист мимического жанра, Артист спортивно-акробатического жанра"},
                        new String[] {"Балетмейстер, Хореограф"},
                        new String[] {"Ведущий программы новостей, Актёр дубляжа, Диктор редакции радиовещания, Ведущий ток-шоу"},
                        new String[] {"Художник-иллюстратор, Художник-живописец, Художник-реставратор, Карикатурист, Скульптор, Художник"},
                        new String[] {"Разрисовщик игрушек, Разрисовщик по стеклу, Гравировщик по стеклу, Оформитель витрин"},
                        new String[] {"Каскадёр, Суфлёр"},
                        new String[] {"Артист ансамбля песни и танца, Артист-вокалист (оперный и камерный)"},
                        new String[] {"Автор сценария, Драматурк, Кинокритик, Очеркист, Писатель, Писатель-романист, Поэт, Сценарист"},
                        new String[] {"Газетный репортёр, Корреспондент, Журналист"},
                        new String[] {"Хранение"}
                ).resizeKeyboard(true));

                this.execute(response);
                break;
            }

            case "Актёр, Артист драмы, Артист мимического жанра, Артист спортивно-акробатического жанра": {
                lite(chatId,"Требования: 1)запоминание текста; 2)вхождение в роль; 3)участие в репетициях; 4)выступления перед зрителями 1)производство представлений; 2)групповая работа; 3)актерская работа; 4)работа залов.", "Производство - 2");
                break;
            }

            case "Балетмейстер, Хореограф": {
                lite(chatId,"Требования: 1)пересказ смешных историй; 2)развлечение зрителей; 3)представление номеров; 4)дрессировка животных 1)живые выступления; 2)театральные представления; 3)групповая работа; 4)работа залов.", "Производство - 2");
                break;
            }

            case "Ведущий программы новостей, Актёр дубляжа, Диктор редакции радиовещания, Ведущий ток-шоу": {
                lite(chatId,"Требования: 1)создание танцев; 2)исполнение танцев; 3)тренировки и танцевальные занятия; 4)участие в репетициях 1)театральные представления; 2)групповая работа; 3)работа залов; 4)живые представления.", "Производство - 2");
                break;
            }

            case "Художник-иллюстратор, Художник-живописец, Художник-реставратор, Карикатурист, Скульптор, Художник": {
                lite(chatId,"Требования: 1)зачитывание новостей; 2)представление лиц; 3)проведение интервью; 4)изучение материалов 1)производство представлений; 2)групповая работа; 3)актерская работа; 4)работа залов.", "Производство - 2");
                break;
            }

            case "Разрисовщик игрушек, Разрисовщик по стеклу, Гравировщик по стеклу, Оформитель витрин": {
                lite(chatId,"Требования: 1) разработка идей; 2) создание рисунков; 3) постановка моделей; 4) восстановление картин 1)творческая работа; 2)воплощение идеи; 3) реставрация произведений искусства; 4)организация выставок", "Производство - 2");
                break;
            }

            case "Каскадёр, Суфлёр": {
                lite(chatId,"Требования: 1) рисование узоров; 2)рисование вывесок; 3)гравировка узоров; 4)передача эмоций 1)творческая работа; 2)работа художников; 3) реставрация произведений искусства; 4)организация выставок", "Производство - 2");
                break;
            }

            case "Артист ансамбля песни и танца, Артист-вокалист (оперный и камерный)": {
                lite(chatId,"Требования: 1)постановка спектаклей; 2)исполнение трюков; 3)фокусы; 4)гимнастические номера 1)театральные представления; 2)творческая работа; 3)работа залов; 4)живые выступления", "Производство - 2");
                break;
            }

            case "Автор сценария, Драматурк, Кинокритик, Очеркист, Писатель, Писатель-романист, Поэт, Сценарист": {
                lite(chatId,"Требования: 1)создание ритма; 2)преобразование идей; 3)игра на музыкальных инструментах; 4)репетиции; 1)театральные представления; 2)творческая работа; 3)работа залов; 4)живые выступления", "Производство - 2");
                break;
            }

            case "Газетный репортёр, Корреспондент, Журналист": {
                lite(chatId,"Требования: 1)написание произведений; 2)проведение исследований; 3)написание сценариев; 4)выбор материалов для публикации. 1)писательская работа; 2)создание произведений; 3)деятельность авторов; 4)работа залов", "Производство - 2");
                break;
            }

            case "Сохранение - 2": {
                var response = new SendMessage(chatId, "Сохранение - 2");

                response.replyMarkup(new ReplyKeyboardMarkup(
                        new String[] {"Специалист по учету музейных предметов, Палеограф, Археограф, Архивариус"},
                        new String[] {"Видеотекарь (высокой квалификации), Библиотекарь-каталогизатор, bБиблиотекарь, Библеограф"},
                        new String[] {"Помощник библиотекаря"},
                        new String[] {"Директор художественной галереи, Заведующий архивом, Директор музея"},
                        new String[] {"Заведующий парикмахерской, Директор выставочного цента"},
                        new String[] {"Преподаватель дополнительного образования, Преподаватель бальных танцев"},
                        new String[] {"Клишист, Разрисовщик игрушек"},
                        new String[] {"Культура - 2"}
                ).resizeKeyboard(true));

                this.execute(response);
                break;
            }

            case "Специалист по учету музейных предметов, Палеограф, Археограф, Архивариус": {
                lite(chatId,"Требования: 1)сбор новостей; 2)подготовка комментариев к новостям; 3)проведение исследований; 4)согласование правок 1) предоставление услуг; 2)журналистская работа; 3)деятельность докладчиков; 4)организация выступлений", "Сохранение - 2");
                break;
            }

            case "Видеотекарь (высокой квалификации), Библиотекарь-каталогизатор, bБиблиотекарь, Библеограф": {
                lite(chatId,"Требования: 1)сохранение записей; 2)подготовка библиографий; 3обслуживание собраний предметов; 4)составление классификаций 1)музейная работа; 2)составление коллекций; 3)составление каталогов; 4) обеспечение функционирования исторических мест.", "Сохранение - 2");
                break;
            }

            case "Помощник библиотекаря": {
                lite(chatId,"Требования: 1)пополнение собраний книг; 2)помощь в выборе книг; 3)работа с каталогами; 4)подготовка документов. 1)работа читальных залов; 2) обеспечение деятельности архивов; 3)составление коллекций; 4)подбор информации.", "Сохранение - 2");
                break;
            }

            case "Директор художественной галереи, Заведующий архивом, Директор музея": {
                lite(chatId,"Требования: 1)пополнение собраний книг; 2)помощь в выборе книг; 3)работа с каталогами; 4)подготовка документов. 1) обеспечение работы читальных залов; 2) обеспечение деятельности архивов; 3)составление коллекций; 4)подбор информации.", "Сохранение - 2");
                break;
            }

            case "Заведующий парикмахерской, Директор выставочного цента": {
                lite(chatId,"Требования: 1)руководство учреждением; 2)руководство персоналом; 3)планирование бюджета; 4)подбор персонала 1)управление учреждением; 2)организация деятельности; 3)принятие рещений; 4)оперативный контроль", "Сохранение - 2");
                break;
            }

            case "Преподаватель дополнительного образования, Преподаватель бальных танцев": {
                lite(chatId,"Требования: 1)предоставление услуг; 2)руководство персоналом; 3)планирование бюджета; 4)подбор персонала 1)управление компанией; 2)организация деятельности; 3)принятие рещений; 4)оперативный контроль", "Сохранение - 2");
                break;
            }

            case "Клишист, Разрисовщик игрушек": {
                lite(chatId,"Требования: 1) разработка идей; 2) создание рисунков; 3) постановка моделей; 4) восстановление картин 1)творческая работа; 2)воплощение идеи; 3) реставрация произведений искусства; 4)организация выставок", "Сохранение - 2");
                break;
            }

            case "Финансы": {
                var response = new SendMessage(chatId, "Финансы");

                response.replyMarkup(new ReplyKeyboardMarkup(
                        new String[] {"Банковское дело"},
                        new String[] {"Финансы и бухгалтерия"},
                        new String[] {"Хранение и передача знаний"},
                        new String[] {"Помощь по хозяйству - 5"},
                        new String[] {"Хранение"}
                ).resizeKeyboard(true));

                this.execute(response);
                break;
            }

            case "Банковское дело": {
                var response = new SendMessage(chatId, "Банковское дело");

                response.replyMarkup(new ReplyKeyboardMarkup(
                        new String[] {"Председатель ЦБ, Заместитель председателя ЦБ, Директор департамента ЦБ РФ, Заместитель директора департамента ЦБ РФ"},
                        new String[] {"Финансы"}
                ).resizeKeyboard(true));

                this.execute(response);
                break;
            }

            case "Председатель ЦБ, Заместитель председателя ЦБ, Директор департамента ЦБ РФ, Заместитель директора департамента ЦБ РФ": {
                lite(chatId,"Требования: —выпуск денег в обращение и регулирование денежно-кредитной политики страны; —регулирование и контроль денежной массы в обращении; —прием депозитов, используемых для расчетов между финансовыми учреждениями; —контроль за банковскими операциями; − разработка и проведение денежно-кредитной политики государства; − обеспечение функционирования платежных систем; − осуществление валютного регулирования и валютного контроля; − регулирование, контроль и надзор финансового рынка и финансовых организаций, а также иных лиц в пределах компетенции;", "Банковское дело");
                break;
            }

            case "Финансы и бухгалтерия": {
                var response = new SendMessage(chatId, "Финансы и бухгалтерия");

                response.replyMarkup(new ReplyKeyboardMarkup(
                        new String[] {"Директор по инвестиционным проектам, Главный аудитор, Главный комплаенс-контролер"},
                        new String[] {"Аналитик исследователь по ценным бумагам, Консультант по инвестициям"},
                        new String[] {"Руководитель по риск-менеджменту, Управляющий"},
                        new String[] {"Руководитель по разработке методологии и клиентскому анализу, Руководитель кредитного отдела"},
                        new String[] {"Аналитик по облигациям, Финансовый аналитик, Консультант по ценным бумагам"},
                        new String[] {"Бизнес-аналитик, Бизнес-консультант, Консультант по вопросам ведения бизнеса"},
                        new String[] {"Аналитик по управлению персоналом, Аналитик бизнес и трудовых процессов"},
                        new String[] {"Финансовый специалист, Специалист по продажам казначейских сертификатов"},
                        new String[] {"Бухгалтер по налогообложению, Бухгалтер по учёту издержек производства, Бухгалтер"},
                        new String[] {"Аналитик, Аналитик рынка труда, Аналитик по операциям , Инженер-экономист"},
                        new String[] {"Брокер акций и пакетов, Агент по продаже ценных бумаг, Брокер по продаже ценных бумаг , Брокер по ценным бумагам"},
                        new String[] {"Агент по кредитованию, Агент по постпродажному обслуживанию, Инспектор кредитный"},
                        new String[] {"Ассистент бухгалтера, Бухгалтер (средней квалификации), Помощник бухгалтера"},
                        new String[] {"Оценщик, Оценщик недвижимости , Оценщик-эксперт, Эксперт по претензиям, Эксперт саморегулируемой организации оценщиков"},
                        new String[] {"Агент по оформлению страхования, Агент по страховым продажам, Агент по финансовому планированию"},
                        new String[] {"Агент по недвижимости, Агент по операциям с недвижимостью, Агент по продаже земельной собственности"},
                        new String[] {"Банковский кассир , Валютный кассир, в банке, Инспектор (по кассовой работе), Кассир, в банке"},
                        new String[] {"Служащий по поддержке операционных процессов (в банке), Служащий по учету, Техник по учету"},
                        new String[] {"Оператор по сортировке банкнот, Служащий по банковским операциям, Андеррайтер (средней квалификации)"},
                        new String[] {"Финансы"}

                ).resizeKeyboard(true));

                this.execute(response);
                break;
            }

            case "Директор по инвестиционным проектам, Главный аудитор, Главный комплаенс-контролер": {
                lite(chatId,"Требования: - оформление коммерческих сделок между коммерческими или другими предприятиями; —подготовка или аудит финансовых счетов; —анализ счетов и подтверждение их точности; —подготовка для частных лиц или предприятий деклараций о подоходном налоге; осуществление планирования, управления и координации финансовой деятельности предприятия или организации; оценка финансового положения предприятия или организации, подготовка бюджетов и контроль за финансовыми операциями; разработка и управление бюджетами, контроль расходов и обеспечение эффективного использования ресурсов; планирование и руководство повседневной деятельностью", "Финансы и бухгалтерия");
                break;
            }

            case "Аналитик исследователь по ценным бумагам, Консультант по инвестициям": {
                lite(chatId,"Требования: деятельность по обработке финансовых операций и проведению расчетов в отношении таких операций: операции по кредитным картам; —услуги консультантов по инвестиционным вопросам; собеседование с клиентами для выяснения финансового положения и целей, определения рискоустойчивости и получения другой информации, необходимой для разработки финансовых планов и инвестиционных стратегий; постановка финансовых целей, а также разработка и претворение в жизнь стратегий для их достижения; мониторинг экономической эффективности капиталовложений; анализ и пересмотр планов капиталовложений на основе пересмотренных потребностей и изменений на рынке; предоставление рекомендаций и согласование страхового покрытия для клиентов.", "Финансы и бухгалтерия");
                break;
            }

            case "Руководитель по риск-менеджменту, Управляющий": {
                lite(chatId,"Требования: —управление пенсионными фондами; —управление взаимными фондами; —управление прочими инвестиционными фондами; − обеспечение административного, стратегического планирования и операционной поддержки, исследований и рекомендаций для высшего руководства по таким вопросам, как управление рисками; − развитие и управление административными и материальными ресурсами организации; − анализ сложных вопросов и инициатив по управлению ресурсами, влияющих на организацию, подготовка соответствующих отчетов, − предоставление информации и поддержка подготовки финансовой отчетности и бюджетов; ведение переписки и оформление представлений;", "Финансы и бухгалтерия");
                break;
            }

            case "Руководитель по разработке методологии и клиентскому анализу, Руководитель кредитного отдела": {
                lite(chatId,"Требования: получение депозитов и/или близких аналогов депозитов и предоставление кредитов или ссужение денежных средств; услуги сберегательных банков ; планирование, руководство и координация деятельности персонала в филиалах; установление и поддержание отношений с индивидуальными и корпоративными клиентами; рассмотрение, оценка и оформление кредитных и страховых заявок; наблюдение за потоком денежных средств и финансовых инструментов ; подготовка финансовой и управленческой отчетности;", "Финансы и бухгалтерия");
                break;
            }

            case "Аналитик по облигациям, Финансовый аналитик, Консультант по ценным бумагам": {
                lite(chatId,"Требования: деятельность по обработке финансовых операций и проведению расчетов в отношении таких операций, включая операции по кредитным картам; услуги консультантов по инвестиционным вопросам; анализ финансовой информации для получения прогнозов коммерческих, промышленных и экономических условий с целью принятия решений о капиталовложениях, анализ рисков; интерпретация данных, связанных с программами капиталовложений, включая цены, прибыль, устойчивость, дальнейшие перспективы в плане инвестиционных рисков и экономического влияния; мониторинг экономических, промышленных и корпоративных изменений посредством анализа информации из финансовых изданий и служб, от инвестиционных банковских фирм, государственных органов, из отраслевых изданий, от сотрудников компаний и из личных собеседований; рекомендации по вопросам капиталовложений и сроков инвестиций для компаний, персонала инвестиционных фирм или инвесторов; действие в интересах клиента; утверждение или отклонение, или координация утверждения или отклонения кредитных линий предоставления коммерческих, ипотечных и потребительских кредитов;", "Финансы и бухгалтерия");
                break;
            }

            case "Бизнес-аналитик, Бизнес-консультант, Консультант по вопросам ведения бизнеса": {
                lite(chatId,"Требования: деятельность по обработке финансовых операций и проведению расчетов в отношении таких операций, включая операции по кредитным картам; —услуги консультантов по инвестиционным вопросам; содействие и стимулирование в области определения целей, стратегий и планов, направленных на удовлетворение запросов потребителей и рациональное использование ресурсов организации; анализ и оценка действующих систем управления и организационных структур; обсуждение с персоналом рациональности действующих систем управления и оценка их эффективности на всех уровнях организации; предоставление клиентам рекомендаций по вопросам эффективной организации и разработке решений организационных проблем", "Финансы и бухгалтерия");
                break;
            }

            case "Аналитик по управлению персоналом, Аналитик бизнес и трудовых процессов": {
                lite(chatId,"Требования: консультирование; работа с кадрами, связанная с наймом работников, комплектованием рабочих мест, обучением, продвижением по службе, вознаграждением, отношениями руководителей и работников и другими областями кадровой политики; изучение и анализ работы, выполняемой в организации, различными способами; собеседование с рабочими, вышестоящими должностными лицами и руководителями; составление подробных описаний должностей, работ и занятий на основании полученных данных; подготовка информации о занятиях или разработка систем классификации занятий; разработка, координация, составление графиков и проведение учебных и развивающих программ, которые могут проводиться в форме индивидуальных или коллективных занятий, а также помощь в проведении семинаров, заседаний, показов и конференций; изучение и консультирование отдельных лиц относительно возможностей трудоустройства, выбора карьерных предпочтений и дальнейшего обучения или подготовки", "Финансы и бухгалтерия");
                break;
            }

            case "Финансовый специалист, Специалист по продажам казначейских сертификатов": {
                lite(chatId,"Требования: оценка потребностей клиентов и ресурсов; рекомендация соответствующих товаров и услуг; подготовка отчетов и предложений в рамках торговых презентаций для демонстрации преимуществ от использования товаров или услуг; назначение и обсуждение цен и условий кредитования, подготовка и применение договоров купли-продажи; проведение консультаций с клиентами после продажи для надлежащего решения любых проблем; обеспечение непрерывной поддержки.", "Финансы и бухгалтерия");
                break;
            }

            case "Бухгалтер по налогообложению, Бухгалтер по учёту издержек производства, Бухгалтер": {
                lite(chatId,"Требования: оформление коммерческих сделок между коммерческими или другими предприятиями; —подготовка или аудит финансовых счетов; —анализ счетов и подтверждение их точности; —подготовка для частных лиц или предприятий деклараций о подоходном налоге; консультирование, планирование и внедрение бюджетного и бухгалтерского контроля, а также других политик и систем бухгалтерской деятельности; подготовка и заверение финансовых отчетов для представления руководству, акционерам и иным органам, предусмотренным законодательством; подготовка или представление отчетности по прогнозированию доходов и бюджету; проверка отчетности и бухгалтерских документов;", "Финансы и бухгалтерия");
                break;
            }

            case "Аналитик, Аналитик рынка труда, Аналитик по операциям , Инженер-экономист": {
                lite(chatId,"Требования: − прогнозирование изменений экономических условий для составления краткосрочных бюджетов, Долгосрочное планирование; оценка капиталовложений; − подготовка рекомендаций, экономических программ и планов, корпоративных стратегий и капиталовложений анализ технико-экономической осуществимости проектов; − мониторинг экономических данных для оценки эффективности и предоставления рекомендаций, касающихся соответствия кредитно-денежной и налоговой политики; − подготовка прогнозов доходов и расходов, процентных ставок и обменных курсов;", "Финансы и бухгалтерия");
                break;
            }

            case "Брокер акций и пакетов, Агент по продаже ценных бумаг, Брокер по продаже ценных бумаг , Брокер по ценным бумагам": {
                lite(chatId,"Требования: —осуществление операций на финансовых рынках по поручению других (например, брокерских операций на фондовой бирже) и связанная с этим деятельность; —брокерские операции с ценными бумагами; —брокерские операции с товарными контрактами; получение информации о финансовом положении клиентов и компаний, в которые могут быть произведены инвестиции; анализ тенденций рынка ценных бумаг, облигаций, акций и других финансовых инструментов, в том числе в иностранной валюте; информирование потенциальных клиентов о рыночных условиях и перспективах; консультирование и участие в переговорах об условиях организации получения/выдачи кредитов и размещения акций и облигаций на финансовом рынке для увеличения капитала клиентов;", "Финансы и бухгалтерия");
                break;
            }

            case "Агент по кредитованию, Агент по постпродажному обслуживанию, Инспектор кредитный": {
                lite(chatId,"Требования: —услуги ипотечных консультантов и брокеров ; получение депозитов и/или близких аналогов депозитов и предоставление кредитов или ссужение денежных средств; —услуги сберегательных банков; —предоставление кредитов на покупку домов специализированными учреждениями, депонирующими денежные вклады; переговоры с заявителями на получение кредитов на личные нужды, ипотечных, студенческих и бизнес-кредитов; изучение и оценка финансового состояния заемщика, рекомендаций, кредитной истории и кредитоспособности; представление кредита и кредитных заявок руководству с рекомендациями для одобрения или отклонения;", "Финансы и бухгалтерия");
                break;
            }

            case "Ассистент бухгалтера, Бухгалтер (средней квалификации), Помощник бухгалтера": {
                lite(chatId,"Требования: оформление коммерческих сделок между коммерческими или другими предприятиями; —подготовка или аудит финансовых счетов; —анализ счетов и подтверждение их точности; —подготовка для частных лиц или предприятий деклараций о подоходном налоге; осуществление учета всех финансовых операций предприятия в соответствии с общими принципами бухгалтерского учета, под руководством бухгалтеров; проверка правильности оформления и ведения документов и записей, касающихся платежей, квитанций и других финансовых операций; подготовка финансовой документации и отчетности на определенный срок; выполнения бухгалтерских и связанных с ними расчетов на компьютере с использованием стандартных пакетов программного обеспечения.", "Финансы и бухгалтерия");
                break;
            }

            case "Оценщик, Оценщик недвижимости , Оценщик-эксперт, Эксперт по претензиям, Эксперт саморегулируемой организации оценщиков": {
                lite(chatId,"Требования: —оценка страховых претензий, установление размера претензий; оценка риска стоимостная оценка риска и ущерба; составление диспаш и определение размеров ущерба; —урегулирование страховых претензий; определение качества или стоимости сырья, недвижимости, промышленного оборудования, личного и домашнего имущества произведений искусства, драгоценных камней и других предметов; оценка размера ущерба или убытков и обязательств страховых компаний и страховщиков в отношении потерь, охватываемых страховым полисом; проверка предметов или имущества для оценки состояния, размера и конструкции;подготовка отчетов об оценке, в которых изложена оценка факторов и используемые методы.", "Финансы и бухгалтерия");
                break;
            }

            case "Агент по оформлению страхования, Агент по страховым продажам, Агент по финансовому планированию": {
                lite(chatId,"Требования: —деятельность страховых агентов и брокеров (то есть страховых посредников) по продаже аннуитетов и полисов страхования и перестрахования, ведение переговоров для заключения договоров или поиску новых клиентов; —оценка страховых претензий установление размера претензий, оценка риска, стоимостная оценка риска и ущерба; составление диспаш и определение размеров ущерба; —урегулирование страховых претензий; получение необходимой информации об обстоятельствах клиентов для определения соответствующего вида страхования и условий страхования; ведение переговоров с клиентами для определения типа и величины страховых рисков, страхование которых является обязательным; объяснение деталей и условий страхования, размеров страховых выплат для покрытия рисков и выгоды для клиентов;", "Финансы и бухгалтерия");
                break;
            }

            case "Агент по недвижимости, Агент по операциям с недвижимостью, Агент по продаже земельной собственности": {
                lite(chatId,"Требования: —деятельность агентов и брокеров по операциям с недвижимым имуществом ; —посреднические услуги по покупке, продаже и сдаче внаем недвижимого имущества за вознаграждение или на договорной основе; —управление недвижимым имуществом за вознаграждение или на договорной основе; —услуги по оценке стоимости недвижимого имущества на объектах недвижимости и другой собственности с целью продажи или сдачи в аренду, требованиях владельцев и запросах потенциальных покупателей или арендаторов; поиск для собственности, которую собираются продать или сдать в аренду, потенциальных покупателей или арендаторов, объяснение им условий продажи и условий аренды или лизинга; разработка и реализация лизинговых соглашений и оценка расходов; организация подписания договоров аренды и передачи прав собственности;", "Финансы и бухгалтерия");
                break;
            }

            case "Банковский кассир , Валютный кассир, в банке, Инспектор (по кассовой работе), Кассир, в банке": {
                lite(chatId,"Требования: получение депозитов и/или близких аналогов депозитов и предоставление кредитов или ссужение денежных средств; —услуги сберегательных банков; —предоставление кредитов на покупку домов специализированными учреждениями, депонирующими денежные вклады; —услуги почтовых денежных переводов; − обслуживание клиента, связанное с оформлением денежных депозитов и снятия денег, чеков, переводов, счетов, платежей по кредитным картам, денежных переводов, поручительств и других банковских операций; − оформление поступлений денежных средств и их снятия со счетов клиентов; − оплата счетов и осуществление денежных переводов по поручению клиентов; − получение почты, продажа почтовых марок и проведение других расчетных операций в почтовом отделении, таких как оплата счетов, денежные переводы и аналогичные операции", "Финансы и бухгалтерия");
                break;
            }

            case "Служащий по поддержке операционных процессов (в банке), Служащий по учету, Техник по учету": {
                lite(chatId,"Требования: оформление коммерческих сделок между коммерческими или другими предприятиями; —подготовка или аудит финансовых счетов; —анализ счетов и подтверждение их точности; —подготовка для частных лиц или предприятий деклараций о подоходном налоге; − проверка цифр, их регистрация и проверка документов для правильного ввода, обеспечения математической точности и надлежащего кодирования; − работа на компьютере с бухгалтерским программным обеспечением для записи, хранения и анализа информации; − классификация, регистрация и обобщение численных и финансовых данных для составления и ведения финансовой документации с использованием журналов, книг и компьютеров; − составление статистической, финансовой, бухгалтерской и аудиторской отчетности и таблиц, относящихся к таким вопросам, как денежные поступления, расходы, дебиторская и кредиторская задолженность, а также прибыль и убытки.", "Финансы и бухгалтерия");
                break;
            }

            case "Оператор по сортировке банкнот, Служащий по банковским операциям, Андеррайтер (средней квалификации)": {
                lite(chatId,"Требования: —услуги ипотечных консультантов и брокеров; —осуществление операций на финансовых рынках по поручению других (например, брокерских операций на фондовой бирже) и связанная с этим деятельность; —брокерские операции с ценными бумагами; − обработка страховых списков, ведение данных об отмене и утверждении сделок, изменении в политике и платежах; − расчет суммы, средних значений, процентов и других показателей, представление их в требуемой табличной форме; − подготовка финансовых документов, а также начисление процентов, брокерских сборов и задолженности по налогам и сборам; − ведение учета облигаций, акций и других ценных бумаг, купленных или проданных по поручению клиентов или работодателя", "Финансы и бухгалтерия");
                break;
            }

            case "Хранение и передача знаний": {
                var response = new SendMessage(chatId, "Хранение и передача знаний");

                response.replyMarkup(new ReplyKeyboardMarkup(
                        new String[] {"Декан факультета, Директор (начальник) курсов , Директор (начальник) учебной части"},
                        new String[] {"Преподаватель, доцент, профессор макроэкономики, ВУЗ, Преподаватель, доцент, профессор международных отношений, ВУЗ"},
                        new String[] {"Начальник службы оптовой и розничной торговли, Заведующий отделом в торговле, Ментор стартапов, Директор магазина"},
                        new String[] {"Финансы"}

                ).resizeKeyboard(true));

                this.execute(response);
                break;
            }

            case "Декан факультета, Директор (начальник) курсов , Директор (начальник) учебной части": {
                lite(chatId,"Требования: —послесреднее образование, не ведущее к получению высшего образования; второй этап высшего образования (ведущий к получению квалификации для проведения современных научных исследований); организация разработки образовательных программ, основанных на стандартах, установленных органами образования и органами управления образованием; − внедрение систем и процедур контроля за работой школьников и студентов; - внедрение инноваций в обучение; − управление административной и технической деятельностью, касающейся приема студентов и оказания образовательных услуг; − контроль подбора, обучения и наблюдение за персоналом; —первый этап высшего образования (не ведущий к получению квалификации для проведения современных научных исследований)", "Хранение и передача знаний");
                break;
            }

            case "Преподаватель, доцент, профессор макроэкономики, ВУЗ, Преподаватель, доцент, профессор международных отношений, ВУЗ": {
                lite(chatId,"Требования: —послесреднее образование, не ведущее к получению высшего образования —первый этап высшего образования (не ведущий к получению квалификации для проведения современных научных исследований); —второй этап высшего образования (ведущий к получению квалификации для проведения современных научных исследований); -разработка и изменение учебных программ; -подготовка учебных курсов по преподаваемым предметам в соответствии с требованиями; − подготовка и чтение лекций и проведение учебных занятий, семинаров и лабораторных работ; − наблюдение за экспериментальной и практической работой студентов; − подготовка отчетов и ведение таких документов, как табели успеваемости студентов, табели посещаемости, и характеристик учебного процесса;", "Хранение и передача знаний");
                break;
            }

            case "Начальник службы оптовой и розничной торговли, Заведующий отделом в торговле, Ментор стартапов, Директор магазина": {
                lite(chatId,"Требования: 1) определение ассортимента продукции, 2) определение цен; 3) реклама товаров заведения; 4) учета запасов и финансов )управление организацией; 2)организация деятельности; 3)принятие рещений; 4)оперативный контроль", "Хранение и передача знаний");
                break;
            }

            case "Помощь по хозяйству - 5": {
                var response = new SendMessage(chatId, "Помощь по хозяйству");

                response.replyMarkup(new ReplyKeyboardMarkup(
                        new String[] {"Инкассатор, Охранник, Охранник по охране имущества, в т.ч. при его транспортировке, Сотрудник (оператор) службы внутренней безопасности"},
                        new String[] {"Финансы"}

                ).resizeKeyboard(true));

                this.execute(response);
                break;
            }

            case "Инкассатор, Охранник, Охранник по охране имущества, в т.ч. при его транспортировке, Сотрудник (оператор) службы внутренней безопасности": {
                lite(chatId,"Требования: патрулирование помещений и проверка дверей, окон и ворот для предотвращения и выявления признаков несанкционированного доступа; − контроль доступа в заведения, мониторинг и разрешение входа или выхода сотрудников и посетителей, проверка личности и выдача пропусков; − присутствие среди посетителей, клиентов или сотрудников с целью сохранения порядка, защиты имущества от кражи или вандализма и применения правил, действующих в заведении; − сбор и обеспечение безопасной доставки наличности и ценностей в банки, банкоматы и заведения розничной торговли.", "Помощь по хозяйству - 5");
                break;
            }

            case "ЖКХ и инфраструктура": {
                var response = new SendMessage(chatId, "ЖКХ и инфраструктура");

                response.replyMarkup(new ReplyKeyboardMarkup(
                        new String[] {"Организационная поддержка - 7"},
                        new String[] {"Поддержка знаний - 3"},
                        new String[] {"Поддержка по хозяйству - 3"},
                        new String[] {"Хранение"}

                ).resizeKeyboard(true));

                this.execute(response);
                break;
            }

            case "Организационная поддержка - 7": {
                var response = new SendMessage(chatId, "Организационная поддержка");

                response.replyMarkup(new ReplyKeyboardMarkup(
                        new String[] {"Заведующий (начальник) приемной, Начальник финансово-экономического отдела"},
                        new String[] {"Начальник службы (материально-технического снабжения)"},
                        new String[] {"Заведующий ремонтно-механической мастерской"},
                        new String[] {"Заведующий очистными сооружения"},
                        new String[] {"Инженер по гражданскому строительству, Инженер по техническому надзору"},
                        new String[] {"Инженер – теплотехник, Инженер-электрик"},
                        new String[] {"Архитектор зданий и сооружений"},
                        new String[] {"Проектировщик городской инфраструктуры - 2"},
                        new String[] {"Инженер по организации, Логист"},
                        new String[] {"Системный администратор"},
                        new String[] {"Сетевой инженер"},
                        new String[] {"Инспектор по контролю за техническим содержанием зданий"},
                        new String[] {"Руководитель канцелярии, Руководитель служащих по кадровому делопроизводству"},
                        new String[] {"Ресепшионист гостиницы"},
                        new String[] {"Стюардесса, Проводник"},
                        new String[] {"ЖКХ и инфраструктура"}

                ).resizeKeyboard(true));

                this.execute(response);
                break;
            }

            case "Заведующий (начальник) приемной, Начальник финансово-экономического отдела": {
                lite(chatId,"Требования: 1. Управление всеми подразделениями организации; 2. Обсуждение с подчиненными вопросов управления, финансового планирования, работы с персоналом, реализация проектов; 3. Подготовка правовых документов; 4. Представление услуг организации по обслуживанию клиентов; 1. Координация деятельности организации; 2. Осуществление контроля за финансовыми операциями; 3. Проведение консультаций с директорами филиалов и руководителями подразделений по текущим вопросам; 4. Обеспечение эффективного использования ресурсов", "Организационная поддержка - 7");
                break;
            }

            case "Начальник службы (материально-технического снабжения)": {
                lite(chatId,"Требования: 1. Предоставление услуг по финансовому планированию и ведение учетной деятельности; 2. Знание рынка продукции требуемой для деятельности организации; 3. Организация хранения и снабжения различных материалов, инструментов; 4. Снабжение работников организации требуемыми средствами и материалами; 1. Осуществление закупки, хранения и распространения необходимых материалов.; 2. Поддержание необходимых запасов при минимальных затратах; 3. Заключение договоров с поставщиками о требованиях к качеству, стоимости и доставке товаров; 4. Внедрение систем учета, а также обеспечение изменения структуры и пополнения запасов в оптимальные сроки;", "Организационная поддержка - 7");
                break;
            }

            case "Заведующий ремонтно-механической мастерской": {
                lite(chatId,"Требования: 1. Осуществление руководства мастерской по специализированному ремонту машин, техники и оборудования.; 2. Проведение консультаций для сотрудников по условиям ведения технических работ; 3. Составление отчетов по производимым работам и ведение финансовой документации; 4. Обеспечение работоспособности мастерской; 1. Организация своевременного ремонта машин, техники и оборудования; 2. Обновление и модернизация техники вспомогательных служб; 3. Заключение договоров о сервисном обслуживании арендованной техники; 4. Организация контроля за соблюдением требований эксплуатации транспорта и техники", "Организационная поддержка - 7");
                break;
            }

            case "Заведующий очистными сооружения": {
                lite(chatId,"Требования: 1. Обеспечение рабочего состояния коллекторных систем или систем по очистке сточных вод; 2. Руководство эксплуатацией сточных коллекторов и ответственность за сбор и переброску сточных вод в очистительные сооружения; 3. Обеспечение очистки и распределения воды для бытовых и промышленных нужд.; 4. Выявление нарушений в работе очистительных сооружений; 1. Общее руководство службой очистки; 2. Наблюдение за соблюдением процедур; 3. Анализ качества работы персонала очистительных сооружений; 4. Осуществление эффективного использования ресурсов и мощностей очистительных сооружений", "Организационная поддержка - 7");
                break;
            }

            case "Инженер по гражданскому строительству, Инженер по техническому надзору": {
                lite(chatId,"Требования: 1. Ведение проектирования инженерно-технических сооружений; 2. Проведение инженерных изысканий под каждый проект; 3. Выполнение строительного контроля на строительных площадках; 4. Проведение экспертизы проектной документации; 1. Создание проектов мостов, транспортных развязок, гидротехнических и промышленных учреждений; 2. Определение необходимых методов строительства и требуемых материалов; 3. Определение эффективных систем обеспечения безопасности сооружений; 4. Осуществление технического контроля за ремонтом сооружений", "Организационная поддержка - 7");
                break;
            }

            case "Инженер – теплотехник, Инженер-электрик": {
                lite(chatId,"Требования: 1. Консультирование ответственных лиц при строительстве тепло- и электростанций; 2. Осуществление разработок проектов тепло-, гидро и электро- сооружений; 3. Создание способов энергосбережения и повышения энергетической эффективности оборудования; 4. Проведение испытаний целостности электрических и механических систем; 1. Обеспечение правильной работы систем генерации, передачи и распределения электрической энергии; 2. Обеспечение бесперебойной работы энергетического оборудования, электрических и тепловых сетей; 3. Разработка механических систем основанных на электрической тяге; 4. Установление электропроводки в промышленных и других зданиях и объектах", "Организационная поддержка - 7");
                break;
            }

            case "Архитектор зданий и сооружений": {
                lite(chatId,"Требования: 1. Создание чертежей зданий и сооружений; 2. Управление строительными объектами; 3. Разработка архитектурных концепций и проектов; 4. Осуществление действий по созданию архитектурного проекта; 1. Подготовка проектной документации; 2. Согласование проекта строительства с ответственными организациями; 3. Составление необходимых технико-экономических обоснований и расчетов в виде рабочей документации; 4. Решение проблем, касающихся эксплуатации и качества интерьера зданий", "Организационная поддержка - 7");
                break;
            }

            case "Проектировщик городской инфраструктуры - 2": {
                lite(chatId,"Требования: 1. Разработка архитектурных концепций и проектов; 2. Осуществление действий по созданию архитектурного проекта; 3. Осуществление территориального планирования; 4. Разработка ландшафтных проектов; 1. Проведение анализов всех факторов, влияющих на землепользование; 2. Проведение открытых встреч со специалистами, властями и населением; 3. Проведение презентаций проектов; 4. Консультирование всех желающих по вопросам городского планирования и застройки", "Организационная поддержка - 7");
                break;
            }

            case "Инженер по организации, Логист": {
                lite(chatId,"Требования: 1. Организация эффективной перевозки грузов с помощью различных видов транспорта; 2. Осуществление отправки различных объёмов груза; 3. Подготовка транспортной документации; 4. Осуществление посреднических операций по фрахтовке грузового места на судне или в самолете; 1. Подготовка и обоснование оптимальных маршрутов и способов доставки грузов; 2. Проведение анализа качества перевозок и скорости доставки грузов; 3. Осуществление расчёта затрат на формирование и хранение запасов товаров; 4. Взаимодействие с транспортными организациями", "Организационная поддержка - 7");
                break;
            }

            case "Системный администратор": {
                lite(chatId,"Требования: 1. Подготовка компьютерных систем к эксплуатации; 2. предоставление услуг по бесперебойной эксплуатации компьютерных систем клиента; 3. Осуществление настройки программного обеспечения; 4. Ведение деятельности по обследованию и экспертизе компьютерных систем; 1. Выполнение услуг по поддержке и администрированию локальных сетей, вебсайтов и программного обеспечения; 2. Обновление аппаратного и программного системного обучения; 3. диагностика проблем, связанных с аппаратным и программным обеспечением; 4. резервирование данных и аварийное восстановление;", "Организационная поддержка - 7");
                break;
            }

            case "Сетевой инженер": {
                lite(chatId,"Требования: 1. предоставление услуг по размещению информации в локальной сети или в интернете; 2. проектирование и разработка баз данных; 3. осуществление полной обработки данных, предоставленных заказчиком; 4. Создание отчетов на основании анализа данных заказчика; 1. анализ, оценка и мониторинг сетевой инфраструктуры; 2. Улучшение работы программного обеспечения и его последующая интеграция; 3. Осуществление поиска и устранение неисправностей при работе сети и в аварийной ситуации; 4. Проведение установки, конфигурирование, испытание, поддержка и администрирование новых и обновленных сетей, программных приложений баз данных, серверов и рабочих станций", "Организационная поддержка - 7");
                break;
            }

            case "Инспектор по контролю за техническим содержанием зданий": {
                lite(chatId,"Требования: 1. Осуществление контроля за работой сотрудников коммунальных служб; 2. Управление и эксплуатация жилого и нежилого фонда; 3. Ведение административной работы, составление отчетов и необходимой финансовой и технической документации; 4. Участие в деятельности органов местного самоуправления; 1. Инспектирование вновь построенных и находящихся в эксплуатации различных зданий и сооружений, в целях контроля за соблюдением действующих законов, строительных норм и правил, государственных стандартов, правил пожарной безопасности и других нормативных актов; 2. Участие в проведении пожарных расследований и установлении причин возгораний имущества; 3. Инициирование мер по поддержанию или улучшению санитарно-гигиенических условий на подведомственной территории; 4. Инициирование мер по очистке общественных мест и удалению отходов", "Организационная поддержка - 7");
                break;
            }

            case "Руководитель канцелярии, Руководитель служащих по кадровому делопроизводству": {
                lite(chatId,"Требования: 1. Осуществление ежедневных административных услуг; 2. Осуществление подготовки документов; 3. Оказание помощи по запросу клиента или рассмотрения жалоб клиента в отдельном порядке; 4. Управление и организация событий: деловых встреч, переговоров, конференций, подбор персонала; 1. Координация и анализ работы служащих; 2. Набор и воздание необходимой документации, рассылка и приём почты; 3. Решение проблем административного характера; 4. Обучение и инструктаж работников", "Организационная поддержка - 7");
                break;
            }

            case "Ресепшионист гостиницы": {
                lite(chatId,"Требования: 1. Предоставление мест посетителям для проживания; 2. Оказание услуг по предоставлению приезжим помощи в размещение и информирование об услугах организации; 3. Осуществление административных услуг по оформлению гостей; 4. Обработка входящих звонков и переадресация их абоненту в номер; 1. Приветствие и регистрация клиентов, посетителей или гостей; 2. Предоставление информации об услугах, ценах и других вопросах; 3. Выполнение запросов клиентов и разрешение конфликтов; 4. Получение и пересылка сообщений;", "Организационная поддержка - 7");
                break;
            }

            case "Стюардесса, Проводник": {
                lite(chatId,"Требования: 1. Контроль за поведением пассажиров в железнодорожном транспорте; 2. Оказание услуг по размещению пассажиров; 3. Обеспечение контроль за поведением пассажиров во время полета, взлета и посадки воздушного судна; 4. Предоставления питания и питья в салоне; 1. Проверка билетов или посадочных талонов и сопровождение пассажиров к их местам или каютам; 2. Объявление, объяснение или демонстрация правил безопасности и порядка действий в аварийной ситуации; 3. Обеспечение комфортных условия для пассажиров; 4. Подготовка пассажиров к отправки и прибытию", "Организационная поддержка - 7");
                break;
            }

            case "Поддержка знаний - 3": {
                var response = new SendMessage(chatId, "Поддержка знаний");

                response.replyMarkup(new ReplyKeyboardMarkup(
                        new String[] {"Преподаватель ВУЗа"},
                        new String[] {"ЖКХ и инфраструктура"}

                ).resizeKeyboard(true));

                this.execute(response);
                break;
            }

            case "Преподаватель ВУЗа": {
                lite(chatId,"Требования: 1. Подготовка высококвалифицированных кадров; 2. Осуществление научных испытаний объектов исследования; 3. Осуществление научных разработок совместно со студентами; 4. Осуществление профессионального обучения для работы с конкретным оборудованием; 1. разработка и изменение учебных программ; 2.       подготовка и чтение лекций; 3. развитие у студентов независимого мышления; 4. наблюдение за практической работой студентов", "Поддержка знаний - 3");
                break;
            }

            case "Поддержка по хозяйству - 3": {
                var response = new SendMessage(chatId, "Поддержка по хозяйству");

                response.replyMarkup(new ReplyKeyboardMarkup(
                        new String[] {"Техник электрик, Техник теплотехник"},
                        new String[] {"Техники-электроники"},
                        new String[] {"Счетоводы и помощники бухгалтеров"},
                        new String[] {"Агент по продажам и изучению потребителей, Агент по сервисному обслуживанию клиентов"},
                        new String[] {"Агент по снабжению, Закупщик"},
                        new String[] {"Экспедитор"},
                        new String[] {"Юрисконсульт (средней квалификации)"},
                        new String[] {"Оператор службы поддержки клиентов"},
                        new String[] {"Специалист-техник по Web"},
                        new String[] {"Техник по телекоммуникационным системам"},
                        new String[] {"Офисный служащий (общий профиль), Служащий канцелярии"},
                        new String[] {"Служащий по счетам к оплате, Табельщик Учетчик"},
                        new String[] {"Служащий по графикам поставки  (материалов), Служащий по приёму заказов"},
                        new String[] {"Дежурный по депо подвижного состава, Дежурный по сопровождению воздушных судов"},
                        new String[] {"Почтальон, Курьер"},
                        new String[] {"Электрик"},
                        new String[] {"Электромеханик"},
                        new String[] {"Машинист электровоза"},
                        new String[] {"Дежурный стрелочного поста, Регулировщик скорости движения вагонов"},
                        new String[] {"Водитель автобуса"},
                        new String[] {"Водитель грузового автомобиля"},
                        new String[] {"Дворник - 2"},
                        new String[] {"Грузчик, Погрузчик, Упаковщик"},
                        new String[] {"Уборщик, Работник по сборки и утилизации мусора"},
                        new String[] {"ЖКХ и инфраструктура"}

                ).resizeKeyboard(true));

                this.execute(response);
                break;
            }

            case "Техник электрик, Техник теплотехник": {
                lite(chatId,"Требования: 1. Участие в создании линий электропередач; 2. Осуществление строительства ГЭС и ТЭЦ; 3. Обеспечение работы распределяющей системы; 4. Исследование и анализ целостных механических и электрических систем; 1. Завершение и компоновка чертежей и схем электро- и теплосетей для проектов.; 2. Обеспечение технического контроля за работой оборудования; 3. Отладка и запуск нового оборудования и оборудования после ремонта; 4. Тестирование, ремонт и усовершенствование рабочего оборудования", "Поддержка по хозяйству - 3");
                break;
            }

            case "Техники-электроники": {
                lite(chatId,"Требования: 1. Применение в работе специализируемого оборудования; 2. Осуществление сборки оборудования на производственном процессе; 3. Осуществление установки высокотехнологичного оборудования; 4. Осуществление демонтажа крупно-масштабных машин и оборудования; 5. оказание технической помощи специалистам при разработке и тестировании; 6. Завершение и компоновка чертежей и схем электро- и теплосетей для проектов.; 7. Осуществление контроля за безопасностью работы нового оборудования; 8 Проведение испытаний электронных схем для помощи специалистам", "Поддержка по хозяйству - 3");
                break;
            }

            case "Счетоводы и помощники бухгалтеров": {
                lite(chatId,"Требования: 1. Составление бухгалтерской отчетности; 2. Проведение внутреннего аудита; 3. Осуществление обработки финансовой информации предоставленной заказчиков; 4. Составление отчетов по запросам заказчиков; 1. Осуществление учета всех финансовых операций предприятия; 2. Проведение проверки оформления и ведения документов и записей, касающихся платежей, квитанций и других финансовых операций; 3. Подготовка финансовой отчетности в определённые сроки; 4. Применение знаний принципов и практики бухгалтерского учета", "Поддержка по хозяйству - 3");
                break;
            }

            case "Агент по продажам и изучению потребителей, Агент по сервисному обслуживанию клиентов": {
                lite(chatId,"Требования: 1. Осуществление торговых сделок по поставке электроэнергии; 2. Организация продажи ресурсов на бирже; 3. Использование способов привлечения и удержания клиентов с помощью сервиса организации; 4. Общение с клиентами и выявление потребностей в оказании услуг по уборке, ремонту или усовершенствованию его помещений; 1. Получение информации об условиях на рынке услуг, а также товарах и услугах конкурентов; 2. Своевременное предоставление клиентам информации об услугах и товарах; 3. Осуществление торговых операций и сопутствующих им действий; 4. Сопровождение клиентов и своевременное оказание им помощи", "Поддержка по хозяйству - 3");
                break;
            }

            case "Агент по снабжению, Закупщик": {
                lite(chatId,"Требования: 1. Осуществление закупок через интернет; 2. Осуществление закупок химических средств для работы сотрудников жилищно-коммунального хозяйства; 3. Поиск поставщиков и покупка строительного материала; 4. Деятельность агентов по оптовой торговле электротоварами и бытовыми электроустановочными изделиями; 1. Получение информации о потребностях и количестве запасов необходимых материалов, средств и инструментов организации; 2. Приобретение необходимого оборудования; 3. Контроль за распределением товаров по филиалам организации; 4. Установление графиков поставок, мониторинг выполнения договоров и связь с поставщиками", "Поддержка по хозяйству - 3");
                break;
            }

            case "Экспедитор": {
                lite(chatId,"Требования: 1. Осуществление деятельности экспедиторов на воздушном, водном и сухопутном транспорте; 2. Организация отправки грузов до конкретных пунктов назначения; 3. Формирование инфраструктуры под складирование и хранение товаров; 4. Создание системы складирования товаров внутри помещений; 1. Проведение таможенного оформления грузов; 2. Обеспечение соблюдения продавцами правил страхования грузов; 3. Оформление экспортно-импортных лицензий и соблюдения других формальностей; 4. Проведение проверки экспортно-импортной документации для определения содержимого груза.", "Поддержка по хозяйству - 3");
                break;
            }

            case "Юрисконсульт (средней квалификации)": {
                lite(chatId,"Требования: 1. Представление клиентов в судебных органах; 2. консультирование и представительство в гражданских и уголовных делах; 3. Подготовка юридических документов; 4. Подготовка к продаже недвижимого имущества; 1. Сбор данных для подготовки документов и передачи в суд; 2. Консультирование клиентов по юридическим вопросам; 3. Изучение новых законов и документов по вопросам наследования, ипотечных кредитов, судебных решений, подлинности прав на собственность; 4. Подготовка документов связанных с передачей недвижимого имущества", "Поддержка по хозяйству - 3");
                break;
            }

            case "Оператор службы поддержки клиентов": {
                lite(chatId,"Требования: 1. Совершение и прием звонков клиентов в целях рекламы или продажи; 2. Оказание услуг помощи и поддержки клиентов, предоставление необходимой информации; 3.Предоставление услуг по удаленному управлению и наладке оборудования клиента; 4. Оказание справочно-информационных услуг по заполнению и приему заявок; 1. Удаленное обслуживание и устранение неполадок в сетевых системах; 2. Помощь пользователям сети в устранении проблем по передаче данных; 3. Выявление зон, нуждающихся в обновлении оборудования, путем обзвона или интернет опроса населения; 4. Проверка работы системы, оборудования, программ или сайтов по запросу клиентов", "Поддержка по хозяйству - 3");
                break;
            }

            case "Специалист-техник по Web": {
                lite(chatId,"Требования: 1. Тестирование и поддержка программного обеспечения и его совместимость с оборудованием; 2. Оказание помощи в разработке компьютерных программ под определенный вид оборудования; 3. Разработка решений для совмести программ и оборудования; 4. Оказание помощи в настройке программ для эффективной передачи данных по сети; 1. Мониторинг надёжности интранет и интернет сайтов или Web-серверного оборудования; 2. Осуществление мер и соблюдение условий для бесперебойной работы серверного и сетевого оборудования; 3. Проведение тестирования оборудования и выработка рекомендаций для повышения производительности; 4. Выполнение Web-серверного резервного копирования и восстановления данных", "Поддержка по хозяйству - 3");
                break;
            }

            case "Техник по телекоммуникационным системам": {
                lite(chatId,"Требования: 1. Проектирование телекоммуникационных систем; 2. Монтаж телекоммуникационных систем на объектах; 3. Обслуживание и ремонт оборудования; 4. Деятельность по разработке информационных и телекоммуникационных систем и оборудования; 1. оказание технической помощи, связанной с исследованиями телекоммуникационного оборудования или тестированием прототипов; 2. Изучение чертежей и эскизов для понимания будущего оборудования или инфраструктуры коммуникаций; 3. Составление смет на монтаж оборудования; 4. Обеспечение технического надзора за эксплуатацией оборудования", "Поддержка по хозяйству - 3");
                break;
            }

            case "Офисный служащий (общий профиль), Служащий канцелярии": {
                lite(chatId,"Требования: 1. Подготовка документов; 2. Редактирование или корректирование документов; 3. Осуществление набора и обработки текстов; 4. Услуги копирования документов, их подготовку к отправке; 1. Обеспечение своевременной обработки поступающей и отправляемой корреспонденции, ее доставка по назначению внутри организации; 2. Регистрация, учет, хранение и передача в соответствующие подразделения документов, приказов и распоряжений руководства; 3. Осуществление ответов на телефонные или электронные запросы или переадресация на соответствующих лиц; 4. Составление отчетов, справок, сводок по результатам контроля за исполнением поручений", "Поддержка по хозяйству - 3");
                break;
            }

            case "Служащий по счетам к оплате, Табельщик Учетчик": {
                lite(chatId,"Требования: 1. Ведение бухгалтерских счетов; 2. Прием платежных средств от клиентов, работа с банками; 3. Подготовка документов для транспортировки грузов; 4. Осуществление вспомогательной деятельности связанной с подготовкой документов для их отправки; 1. Знание бухгалтерских программ и ведение в них расчетов; 2. Проведение расчетов и ведение финансовой отчетности; 3. Ведение учета рабочего времени сотрудников, отпусков и сверхурочных для расчета заработной платы; 4. Сбор и фиксирование любой документации связанной с финансовой деятельностью организации", "Поддержка по хозяйству - 3");
                break;
            }

            case "Служащий по графикам поставки  (материалов), Служащий по приёму заказов": {
                lite(chatId,"Требования: 1. Организация перевозки грузов сухопутным, водным или воздушным транспортом; 2. Организация отправки товаров партиями или отдельными экземплярами; 3. Подготовка транспортной документации или путевых листов; 4. Отправка особо ценной документации или грузов с охраной, содержащих государственную, служебную или коммерческую тайны; 1. Осуществление приема заказов по доставке, расчет всех необходимых составляющих, их количества и качества материалов, компонентов или готовых изделий; 2. Выполнение контроля за соблюдением графика поставок; 3. Подготовка или помощь в составлении и утверждении графиков поставок; 4. Осуществление диагностики процесса поставок и оценка эффективности графика", "Поддержка по хозяйству - 3");
                break;
            }

            case "Дежурный по депо подвижного состава, Дежурный по сопровождению воздушных судов": {
                lite(chatId,"Требования: 1. Осуществление вспомогательной деятельности по управлению депо, вокзалами, портами и аэропортами, станциями и доками.; 2. Обеспечение работ по эксплуатации и обслуживание всех транспортных средств в организации; 3. Управление техническими службами аэропорта и общее руководство воздушным движением; 4. Обеспечение руководства наземными службами аэродромов и космодромов; 1. Ведение оперативного учета эксплуатации транспортных средств; 2. Осуществление координации движения и перемещений транспортных средств внутри выделенной зоны и ведение учета их перемещений; 3. Планирование и распределение движения транспортных средств; 4. Составление списков пассажиров и грузовых деклараций", "Поддержка по хозяйству - 3");
                break;
            }

            case "Почтальон, Курьер": {
                lite(chatId,"Требования: 1. Осуществление услуг по приему, пересылке, доставке (вручению) различных письменных сообщений; 2. Осуществление услуг по приему, пересылке, доставке (вручению) посылок, бандеролей, предметов и товаров почтовых отправлений; 3. Осуществление почтовых отправлений и переводов; 4. Предоставление услуг по доставке почты на дом; 1. Выполнение обработки почтовых заказов; 2. Осуществление сортировки почтовых отправлений; 3. Предоставление отчетов, подтверждающих доставку, по запросу клиента; 4. Осуществление сортировки и хранения простой входящей и исходящей корреспонденции и отправка исходящей почты в различные учреждения.", "Поддержка по хозяйству - 3");
                break;
            }

            case "Электрик": {
                lite(chatId,"Требования: 1. Проведение испытаний качества и надежности изделий и работоспособности готовой продукции; 2. Знание качества материалов и выбор соответствующих изделий для электро технических работ; 3. Проверка целостности электрических систем; 4. Осуществление технического контроля за работоспособностью оборудования; 1. монтаж, техническое обслуживание и ремонт систем электропроводки и сопутствующего оборудования в различных зданиях; 2. планирование компоновки и установка электрической проводки; 3. проверка электрических систем и оборудования для выявления дефектов; 4. определение мест для расположения электрических распределительных щитов", "Поддержка по хозяйству - 3");
                break;
            }

            case "Электромеханик": {
                lite(chatId,"Требования: 1. Выполнение работ по подводке электросетей для подключения электроприборов; 2. Ведение монтажных работ по подключению электропроводки, электроарматуры, телекоммуникаций в различных зданиях; 3. Осуществление электрических цепей на автотранспорте и ином оборудовании; 4. Ведение ремонта электронного и оптического оборудования; 1. Сборка, настройка и ремонт различных видов электрических машин и моторов, генераторов, распределительных устройств; 2. Осуществление проверки работоспособности электродвигателей, электроаппаратов и трансформаторов после ремонта; 3. Проверка и испытание промышленных электрических изделий; 4. Замена и ремонт неисправных компонентов", "Поддержка по хозяйству - 3");
                break;
            }

            case "Машинист электровоза": {
                lite(chatId,"Требования: 1. Управление железнодорожным составом; 2. Осуществление маневровых работ и формирование состава поезда; 3. Осуществление междугородных и международных перевозок; 4. Осуществление грузовых и пассажирских перевозок; 1. вождение или помощь в вождении таких подземных или надземных поездов, как паровоз, электровоз, тепловоз, поезд метрополитена, трамвай и других локомотивов; 2. Регулирования скорости движения состава в зависимости от погоды и виды груза; 3. Управление подвижным составом на основании внешних специализированных сигналов; 4. Организация и выполнение маневровых работ", "Поддержка по хозяйству - 3");
                break;
            }

            case "Дежурный стрелочного поста, Регулировщик скорости движения вагонов": {
                lite(chatId,"Требования: 1. Организация правильной работы железнодорожного оборудования по управлению движением; 2. Организация работы оборудования согласно графика и расписания движений подвижных составов; 3. Помощь в осуществлении железнодорожных маневровых или буксировочных услуг, например, перемещение вагонов между сортировочными станциями, заводскими подъездными путями и т.п; 4. Осуществление услуг по управлению железнодорожной инфраструктурой; 1. Наблюдение и обеспечение безопасности грузовых поездов во время хода; 2. Контроль железнодорожного движения на линейном участке железной дороги путем переключения сигналов и стрелок с пульта управления или блокпоста; 3. Осуществление переключения и сцепки подвижного состава на сортировочных станциях и запасных путях; 4. Помощь в составлении рабочих составов", "Поддержка по хозяйству - 3");
                break;
            }

            case "Водитель автобуса": {
                lite(chatId,"Требования: 1. Осуществление внутригородских, междугородних и международных рейсов; 2. Осуществление перевозок пассажиров на автобусе; 3. Осуществление регулярных поездок по строго определённым маршрутам; 4. Ремонт и обслуживание транспортных средств; 1. Вождение автобусов; 2. Управление различными системами автобуса; 3. Соблюдении правил дорожного движения; 4. Проведение плановых осмотров автобусов", "Поддержка по хозяйству - 3");
                break;
            }

            case "Водитель грузового автомобиля": {
                lite(chatId,"Требования: 1. Осуществление перевозок различных видов грузов автомобильным транспортом; 2. Использование специализированных автотранспортных средств таких как: грузовики - рефрижераторы, бетономешалки, автовозы; 3. Осуществление технической помощи и буксировки на трассе; 4. Проведение обслуживания и ремонта автотранспортного средства; 1. Вождение различных тяжёлых автомашин для транспортировки различных жидкостей, товаров или материалов; 2. Выбор наиболее приемлемого маршрута; 3. Соблюдение верных способов транспортировки и хранения груза; 4. Проведение операций по разгрузке и загрузке товаров и материалов", "Поддержка по хозяйству - 3");
                break;
            }

            case "Дворник - 2": {
                lite(chatId,"Требования: 1. Внешняя уборка территорий; 2. Удаление с дорожек снега и льда; 3. Участие в уборе дворов и придомовых территорий, наведение внешнего порядка; 4. Уборка территорий вокруг многоквартирных домов; 1. Очистка установленных урн; 2. Ведение профилактического осмотра и удаление мусора из специальных камер; 3. Очистка территорий от мелкого бытового мусора и пыли; 4. Уборка территории от ледяных наслоений на проезжей части", "Поддержка по хозяйству - 3");
                break;
            }

            case "Грузчик, Погрузчик, Упаковщик": {
                lite(chatId,"Требования: 1. Погрузка и разгрузка грузов независимо от типа транспорта; 2. Загрузка и разгрузка железнодорожных вагонов; 3. Ведение вспомогательной деятельности при осуществлении перевозок; 4. Проведение обработки грузов, определение конечного места и времени погрузки; 1. Осуществление упаковки для транспортировки товаров; 2. Организация переноса и складирования грузов; 3. Выгрузка товаров и материала на другие средства транспортировки; 4. Перетаскивание и складирование товаров на складах и аналогичных объектах", "Поддержка по хозяйству - 3");
                break;
            }

            case "Уборщик, Работник по сборки и утилизации мусора": {
                lite(chatId,"Требования: 1. Осуществление сбора бытовых отходов и отходов с предприятий; 2. Ведение сбора бытовых и смешанных видов отходов; 3. Помощь в сборе материалов пригодных для вторичного использования; 4. Осуществление перевозки неопасных отходов; 1. Сбор мусора и материалов для вторичной переработки; 2. Проведение уборки улиц, парков, скверов; 3. Передвижение на автомобилях для сбора мусора; 4. Опустошение мусорных контейнеров, их загрузка в уборочную машину", "Поддержка по хозяйству - 3");
                break;
            }

            case "Воспитание": {
                var response = new SendMessage(chatId, "Воспитание");

                response.replyMarkup(new ReplyKeyboardMarkup(
                        new String[] {"Дошкольное воспитание"},
                        new String[] {"Организационная поддержка - 8"},
                        new String[] {"Хранение"}
                ).resizeKeyboard(true));

                this.execute(response);
                break;
            }

            case "Дошкольное воспитание": {
                var response = new SendMessage(chatId, "Дошкольное воспитание");

                response.replyMarkup(new ReplyKeyboardMarkup(
                        new String[] {"Преподаватель (в системе дошкольного воспитания и обучения), Воспитатель, Тьютор, Игропедагог"},
                        new String[] {"Няня, Няня в яслях, Помощник воспитателя, Работник по присмотру за детьми в семьях в дневное время"},
                        new String[] {"Воспитание"}
                ).resizeKeyboard(true));

                this.execute(response);
                break;
            }

            case "Преподаватель (в системе дошкольного воспитания и обучения), Воспитатель, Тьютор, Игропедагог": {
                lite(chatId,"Требования: 1)проведение занятий с детьми; 2)наблюдение за режимом детей; 3)оказание первой медицинской помощи; 4)творческое самовыражение 1)дошкольное образование; 2)введение в школьную среду; 3)обучение грамоте; 4)специальное образование", "Дошкольное воспитание");
                break;
            }

            case "Няня, Няня в яслях, Помощник воспитателя, Работник по присмотру за детьми в семьях в дневное время": {
                lite(chatId,"Требования: 1) сопровождение детей; 2) наблюдение за детьми; 3) помощь детям; 4) ведение документации 1)дошкольное образование; 2)введение в школьную среду; 3)социальная помощь детям; 4)приюты и интернаты для детей.", "Дошкольное воспитание");
                break;
            }

            case "Организационная поддержка - 8": {
                var response = new SendMessage(chatId, "Организационная поддержка");

                response.replyMarkup(new ReplyKeyboardMarkup(
                        new String[] {"Руководитель специализированных подразделений в сфере Образования, Декан факультета, Директор кабинета"},
                        new String[] {"Главный тренер, Директор физкультурно-спортивной организации, Директор фитнес-центра, Заведующий спортивной базой, Ментор стартапов"},
                        new String[] {"Директор центра психолого-педагогической поддержки, Директор центра социального обслуживания - 2"},
                        new String[] {"Директор детского дома, Заведующий детским садом, Заведующий социальной службы для детей - 2"},
                        new String[] {"Личный помощник, Секретарь кабинета, Стенографист, Секретарь широкого профиля"},
                        new String[] {"Администратор фронт-офиса, Бортпроводница (на земле), Ресепшионист гостиницы, Секретарь стоматологии, Специалист Call-центра"},
                        new String[] {"Сетевой администратор, Системный администратор, Архитектор информационных систем, IT-проповедник"},
                        new String[] {"Административной и юридической деятельности, Функциональный руководитель по финансовой"},
                        new String[] {"Адвокат, Аналитик по вопросам интеллектуальной собственности, Нотариус, Медиатор, Патентовед"},
                        new String[] {"Воспитание"}
                ).resizeKeyboard(true));

                this.execute(response);
                break;
            }

            case "Руководитель специализированных подразделений в сфере Образования, Декан факультета, Директор кабинета": {
                lite(chatId,"Требования: 1)разработка программ; 2)прием детей; 3)планирование бюджета; 4)подбор персонала. 1)управление учреждением; 2)организация деятельности; 3)принятие рещений; 4)оперативный контроль", "Организационная поддержка - 8");
                break;
            }

            case "Главный тренер, Директор физкультурно-спортивной организации, Директор фитнес-центра, Заведующий спортивной базой, Ментор стартапов": {
                lite(chatId,"Требования: 1)административный контроль; 2)планирование бюджета; 3)оснащение инвентарём; 4)подбор персонала 1)управление организацией; 2)организация деятельности; 3)принятие рещений; 4)оперативный контроль", "Организационная поддержка - 8");
                break;
            }

            case "Директор центра психолого-педагогической поддержки, Директор центра социального обслуживания - 2": {
                lite(chatId,"Требования: 1)общее руководство; 2)контроль бюджета; 3)взаимодействие с другими службами; 4)подбор персонала 1)управление организацией; 2)организация деятельности; 3)принятие рещений; 4)оперативный контроль", "Организационная поддержка - 8");
                break;
            }

            case "Директор детского дома, Заведующий детским садом, Заведующий социальной службы для детей - 2": {
                lite(chatId,"Требования: 1)разработка программ развития; 2)контроль бюджета; 3)присмотр за детьми; 4)управление материально-технической базой. 1)управление учреждением; 2)организация деятельности; 3)принятие рещений; 4)оперативный контроль.", "Организационная поддержка - 8");
                break;
            }

            case "Личный помощник, Секретарь кабинета, Стенографист, Секретарь широкого профиля": {
                lite(chatId,"Требования: 1)печать документов; 2)работа с программами; 3)хранение документов; 4)работа с почтой 1)подготовка документов; 2)обработка текста; 3)редактирование документов; 4)секретарские услуги", "Организационная поддержка - 8");
                break;
            }

            case "Администратор фронт-офиса, Бортпроводница (на земле), Ресепшионист гостиницы, Секретарь стоматологии, Специалист Call-центра": {
                lite(chatId,"Требования: 1)регистрация клиентов; 2)информирование; 3)работа с жалобами; 4) прием платежей 1)подготовка документов; 2)обработка текста; 3)редактирование документов; 4)секретарские услуги", "Организационная поддержка - 8");
                break;
            }

            case "Сетевой администратор, Системный администратор, Архитектор информационных систем, IT-проповедник": {
                lite(chatId,"Требования: 1)администрирование сетей; 2)диагностика проблем; 3)аварийное восстановление; 4)работа с программным обеспечением. 1)разработка компьютерных систем; 2)управление данными; 3)техническая поддержка; 4)настройка компьютеров", "Организационная поддержка - 8");
                break;
            }

            case "Административной и юридической деятельности, Функциональный руководитель по финансовой": {
                lite(chatId,"Требования: 1)управление организацией; 2)контроль бюджета; 3)подбор персонала; 4)взаимодействие с учреждениями 1)оперативный контроль; 2)анализ счетов; 3)оформление сделок; 4)подготовка деклараций", "Организационная поддержка - 8");
                break;
            }

            case "Адвокат, Аналитик по вопросам интеллектуальной собственности, Нотариус, Медиатор, Патентовед": {
                lite(chatId,"Требования: 1)проведение слушаний; 2) определение прав и обязанностей сторон; 3)вынесение решений; 4)объявление приговора. 1)представление интересов сторон; 2)рассмотрение дел; 3)подготовка документов; 4)юридическая работа", "Организационная поддержка - 8");
                break;
            }

            case "Образовательные технологии": {
                var response = new SendMessage(chatId, "Организационная поддержка");

                response.replyMarkup(new ReplyKeyboardMarkup(
                        new String[] {"Образование в школе"},
                        new String[] {"Образование в жизни"},
                        new String[] {"Помощь по хозяйству - 6"},
                        new String[] {"Организационная поддержка - 9"},
                        new String[] {"Образование высшее"},
                        new String[] {"Хранение"}
                ).resizeKeyboard(true));

                this.execute(response);
                break;
            }

            case "Образование в школе": {
                var response = new SendMessage(chatId, "Образование в школе");

                response.replyMarkup(new ReplyKeyboardMarkup(
                        new String[] {"Учитель средней школы, Учитель по естественным наукам, Учитель по математическим наукам, Учитель по общественным наукам"},
                        new String[] {"Учитель начальных классов, Тьютор, Игропедагог"},
                        new String[] {"Образовательные технологии"}
                ).resizeKeyboard(true));

                this.execute(response);
                break;
            }

            case "Учитель средней школы, Учитель по естественным наукам, Учитель по математическим наукам, Учитель по общественным наукам": {
                lite(chatId,"Требования: 1)разработка учебных программ; 2)проведение уроков; 3)оценка успеваемости; 4)проверка контрольных работ. 1)развитие личности; 2)общее образование; 3)среднее образование; 4)специальное образование.", "Образование в школе");
                break;
            }

            case "Учитель начальных классов, Тьютор, Игропедагог": {
                lite(chatId,"Требования: 1)подготовка планов уроков; 2)поддержание дисциплины; 3)присмотр за детьми; 4)проведение родительских собраний. 1)обучение чтению и письму; 2)начальное образование: 3)специальное образование; 4)обучение искусству и музыке.", "Образование в школе");
                break;
            }

            case "Образование в жизни": {
                var response = new SendMessage(chatId, "Образование в жизни");

                response.replyMarkup(new ReplyKeyboardMarkup(
                        new String[] {"Преподаватель бальных танцев, Преподаватель музыкальной школы"},
                        new String[] {"Преподаватель в области специального образования, Олигофренопедагог, Сурдопедагог, Тифлопедагог, Тренер по майнд-фитнесу"},
                        new String[] {"Специалист-профессионал краткосрочной подготовки, переподготовки и повышения квалификации, Инструктор по обеспечению полетов"},
                        new String[] {"Образовательные технологии"}
                ).resizeKeyboard(true));

                this.execute(response);
                break;
            }

            case "Преподаватель бальных танцев, Преподаватель музыкальной школы": {
                lite(chatId,"Требования: 1)работа кружков; 2)комплектование состава; 3)разработка графиков работы; 4)проведение занятий. 1)образование в сфере искусства; 2)организованное обучение; 3)обучение игре на фортепьяно; 4)обучение танцам.", "Образование в жизни");
                break;
            }

            case "Преподаватель в области специального образования, Олигофренопедагог, Сурдопедагог, Тифлопедагог, Тренер по майнд-фитнесу": {
                lite(chatId,"Требования: 1)оценка возможностей; 2)разработка программ; 3)обучение самообслуживанию; 4)работа с документами. 1)специальное образование; 2)преподавание практических навыков; 3)обучение детей с недостатками развития; 4)обучение грамоте.", "Образование в жизни");
                break;
            }

            case "Специалист-профессионал краткосрочной подготовки, переподготовки и повышения квалификации, Инструктор по обеспечению полетов": {
                lite(chatId,"Требования: 1)разработка программ; 2)обеспечение безопасных условий; 3)проведение аттестации; 4)допуск к работе. 1)специальная подготовка; 2)курсы подготовки; 3)обучение взрослых; 4)летные школы.", "Образование в жизни");
                break;
            }

            case "Помощь по хозяйству - 6": {
                var response = new SendMessage(chatId, "Помощь по хозяйству");

                response.replyMarkup(new ReplyKeyboardMarkup(
                        new String[] {"Помощник учителя, Ассистент по учебно-методической части специального образования, Модератор"},
                        new String[] {"Работник по делам молодёжи, Работник кризисного центра, Работник семейного центра"},
                        new String[] {"Консультант по вопросам профессиональной деятельности, Консультант по вопросам образования"},
                        new String[] {"Аудитор, Бухгалтер, Бухгалтер по налогообложению, Бухгалтер-ревизор, Координатор по бюджету, Экономист по планированию"},
                        new String[] {"Инкассатор, Охранник, Сотрудник (оператор) службы внутренней безопасности"},
                        new String[] {"Образовательные технологии"}
                ).resizeKeyboard(true));

                this.execute(response);
                break;
            }

            case "Помощник учителя, Ассистент по учебно-методической части специального образования, Модератор": {
                lite(chatId,"Требования: 1)разработка программ; 2)обеспечение безопасных условий; 3)проведение аттестации; 4)допуск к работе. 1)специальная подготовка; 2)курсы подготовки; 3)обучение взрослых; 4)летные школы.", "Помощь по хозяйству - 6");
                break;
            }

            case "Работник по делам молодёжи, Работник кризисного центра, Работник семейного центра": {
                lite(chatId,"Требования: 1)присмотр за детьми; 2)подготовка помещений; 3)оказание помощи; 4)подшивка материалов. 1)развитие личности; 2)общее образование; 3)среднее образование; 4)специальное образование.", "Помощь по хозяйству - 6");
                break;
            }

            case "Консультант по вопросам профессиональной деятельности, Консультант по вопросам образования": {
                lite(chatId,"Требования: 1)оценка уровня способностей; 2)подготовка программ; 3)распределение заданий; 4) консультирование студентов. 1) консультирование по вопросам образования; 2)проведение текстов; 3)профориентация; 4)студенческий обмен.", "Помощь по хозяйству - 6");
                break;
            }

            case "Аудитор, Бухгалтер, Бухгалтер по налогообложению, Бухгалтер-ревизор, Координатор по бюджету, Экономист по планированию": {
                lite(chatId,"Требования: 1)сбор информации; 2)консультирование и контроль; 3)оказание помощи; 4)взаимодействие с другими службами. 1)социальные услуги; 2)воспитание детей и подростков; 3)уход за детьми; 4)усыновление и удочерение.", "Помощь по хозяйству - 6");
                break;
            }

            case "Инкассатор, Охранник, Сотрудник (оператор) службы внутренней безопасности": {
                lite(chatId,"Требования: 1)бухгалтерский контроль; 2)подготовка финансовых отчетов; 3)отчёты по доходам; 4)консультирование руководства. 1)подготовка счетов; 2)анализ счетов; 3)подготовка деклараций; 4)консультации.", "Помощь по хозяйству - 6");
                break;
            }

            case "Организационная поддержка - 9": {
                var response = new SendMessage(chatId, "Организационная поддержка");

                response.replyMarkup(new ReplyKeyboardMarkup(
                        new String[] {"Инспектор школ (гимназий, лицеев, техникумов, колледжей), Методист по профессиональным программам"},
                        new String[] {"Руководитель специализированных подразделений в сфере образования, Декан факультета"},
                        new String[] {"Специалисты-профессионалы по методике обучения, Инспектор школ, Методист по профессиональным программам"},
                        new String[] {"Секретарь широкого профиля, Личный помощник, Секретарь кабинета"},
                        new String[] {"Администратор фронт-офиса, Специалист Call-центра, Секретарь стоматологии, Бортпроводница (на земле), Ресепшионист гостиницы"},
                        new String[] {"Сетевой администратор, IT-проповедник, Архитектор информационных систем, Системный администратор"},
                        new String[] {"Функциональный руководитель по финансовой, административной и юридической деятельности, Главный бухгалтер"},
                        new String[] {"Специалист по инженерной психологии, Психолог, промышленный, Психопедагог, Спортивный психолог"},
                        new String[] {"Инспектор по надзору за условно осужденными, Консультант по профессиональной реабилитации"},
                        new String[] {"Эксперт по «образу будущего» ребенка"},
                        new String[] {"Адвокат, Нотариус, Медиатор, Патентовед, Судебный исполнитель, частный, Юрисконсульт, Сетевой юрист, Виртуальный адвокат"},
                        new String[] {"Образовательные технологии"}
                ).resizeKeyboard(true));

                this.execute(response);
                break;
            }

            case "Инспектор школ (гимназий, лицеев, техникумов, колледжей), Методист по профессиональным программам": {
                lite(chatId,"Требования: 1)патрулирование помещений; 2)выдача пропусков; 3)сохранение порядка; 4)предотвращение пожаров. 1) услуги телохранителей 2) охрана и патрулирование 3) обеспечение безопасности; 4) инкассация и доставка денег.", "Организационная поддержка - 9");
                break;
            }

            case "Руководитель специализированных подразделений в сфере образования, Декан факультета": {
                lite(chatId,"Требования: 1)исследовательская работа; 2)проведение семинаров; 3)разработка учебных программ; 4)подготовка отчётов. 1)вспомогательные услуги; 2)консультирование; 3)профориентация; 4)тестирование.", "Организационная поддержка - 9");
                break;
            }

            case "Специалисты-профессионалы по методике обучения, Инспектор школ, Методист по профессиональным программам": {
                lite(chatId,"Требования: 1)разработка программ; 2)прием студентов; 3)планирование бюджета; 4)подбор персонала. 1)управление учреждением; 2)организация деятельности; 3)принятие рещений; 4)оперативный контроль.", "Организационная поддержка - 9");
                break;
            }

            case "Секретарь широкого профиля, Личный помощник, Секретарь кабинета": {
                lite(chatId,"Требования: 1)исследовательская работа; 2)проведение семинаров; 3)разработка учебных курсов; 4)подготовка отчетов. 1)вспомогательные услуги; 2)консультирование; 3)профориентация; 4)тестирование.", "Организационная поддержка - 9");
                break;
            }

            case "Администратор фронт-офиса, Специалист Call-центра, Секретарь стоматологии, Бортпроводница (на земле), Ресепшионист гостиницы": {
                lite(chatId,"Требования: 1)печать документов; 2)работа с программами; 3)хранение документов; 4)работа с почтой. 1)подготовка документов; 2)обработка текста; 3)редактирование документов; 4)секретарские услуги.", "Организационная поддержка - 9");
                break;
            }

            case "Сетевой администратор, IT-проповедник, Архитектор информационных систем, Системный администратор": {
                lite(chatId,"Требования: 1)регистрация клиентов; 2)информирование; 3)работа с жалобами; 4) прием платежей. 1)подготовка документов; 2)обработка текста; 3)редактирование документов; 4)секретарские услуги.", "Организационная поддержка - 9");
                break;
            }

            case "Функциональный руководитель по финансовой, административной и юридической деятельности, Главный бухгалтер": {
                lite(chatId,"Требования: 1) администрирование сетей; 2) диагностика проблем; 3) аварийное восстановление; 4)работа с программным обеспечением. 1)разработка компьютерных систем; 2)управление данными; 3)техническая поддержка; 4)настройка компьютеров.", "Организационная поддержка - 9");
                break;
            }

            case "Специалист по инженерной психологии, Психолог, промышленный, Психопедагог, Спортивный психолог": {
                lite(chatId,"Требования: 1)управление организацией; 2)контроль бюджета; 3)подбор персонала; 4)взаимодействие с учреждениями. 1)оперативный контроль; 2)анализ счетов; 3)оформление сделок; 4)подготовка деклараций.", "Организационная поддержка - 9");
                break;
            }

            case "Инспектор по надзору за условно осужденными, Консультант по профессиональной реабилитации": {
                lite(chatId,"Требования: 1)диагностика нарушений; 2)подготовка отчетов; 3)разработка тестов; 4)проведение опросов 1)вспомогательные услуги; 2)профориентация; 3)оценка систем тестирования; 4)тестирование.", "Организационная поддержка - 9");
                break;
            }

            case "Эксперт по «образу будущего» ребенка": {
                lite(chatId,"Требования: 1)сбор информации; 2)консультирование и контроль; 3)оказание помощи; 4)взаимодействие с другими службами. 1)социальные услуги; 2)воспитание детей и подростков; 3)уход за детьми; 4)усыновление и удочерение.", "Организационная поддержка - 9");
                break;
            }

            case "Адвокат, Нотариус, Медиатор, Патентовед, Судебный исполнитель, частный, Юрисконсульт, Сетевой юрист, Виртуальный адвокат": {
                lite(chatId,"Требования: 1)проведение слушаний; 2) определение прав и обязанностей сторон; 3)вынесение решений; 4)объявление приговора 1)представление интересов сторон; 2)рассмотрение дел; 3)подготовка документов; 4)юридическая работа.", "Организационная поддержка - 9");
                break;
            }

            case "Образование высшее": {
                var response = new SendMessage(chatId, "Образование высшее");

                response.replyMarkup(new ReplyKeyboardMarkup(
                        new String[] {"Преподаватель в области гуманитарных наук, Профессор в области права, Преподаватель в области искусства"},
                        new String[] {"Инженерно-педагогический работник колледжа в области гуманитарных наук, Инженерно-педагогический работник в области права"},
                        new String[] {"Преподаватель теологии, Преподаватель религии"},
                        new String[] {"Образовательные технологии"}
                ).resizeKeyboard(true));

                this.execute(response);
                break;
            }

            case "Преподаватель в области гуманитарных наук, Профессор в области права, Преподаватель в области искусства": {
                lite(chatId,"Требования: 1)разработка программ; 2)чтение лекций; 3)проверка контрольных работ. 4)участие в семинарах. 1)получение высшего образования; 2)получение квалификации; 3)проведение исследований; 4)присвоение ученых степеней.", "Образование высшее");
                break;
            }

            case "Инженерно-педагогический работник колледжа в области гуманитарных наук, Инженерно-педагогический работник в области права": {
                lite(chatId,"Требования: 1)разработка программ; 2)чтение лекций; 3)работа с инструментами 4)проверка проектов. 1)профессиональное образование: 2)техническое образование; 3)обучение поваров; 4)обучение ремонту компьютеров.", "Образование высшее");
                break;
            }

            case "Преподаватель теологии, Преподаватель религии": {
                lite(chatId,"Требования: 1)чтение лекций; 2) наблюдение за практической работой студентов; 3)проверка контрольных работ; 4)организация работы студентов. 1) профессиональное образование; 2) повышение квалификации; 3) специальная подготовка; 4) религиозное образование.", "Образование высшее");
                break;
            }


            case "Коммуникации": {
                var response = new SendMessage(chatId, "Вы выбрали Коммуникацию");

                response.replyMarkup(new ReplyKeyboardMarkup(
                        new String[] {"Путешествия"},
                        new String[] {"Образовательные технологии"}
                ).resizeKeyboard(true));

                this.execute(response);
                break;
            }

            case "Путешествия": {
                var response = new SendMessage(chatId, "Вы выбрали Путешествия");

                response.replyMarkup(new ReplyKeyboardMarkup(
                        new String[] {"Организационная поддержка - 10"},
                        new String[] {"Помощь по хозяйству - 7"},
                        new String[] {"Поддержка знаний - 4"},
                        new String[] {"Обеспечение безопасности - 3"},
                        new String[] {"Коммуникации"}
                ).resizeKeyboard(true));

                this.execute(response);
                break;
            }

            case "Организационная поддержка - 10": {
                var response = new SendMessage(chatId, "Вы выбрали Организационную поддержку");

                response.replyMarkup(new ReplyKeyboardMarkup(
                        new String[] {"Агент по туризму, оператор по бронированию билетов (авиа, жд и пр.), сотрудник выдачи билетов"},
                        new String[] {"Бортпроводник, Проводник, Стюард, Стюардесса"},
                        new String[] {"Кондуктор (автобус, трамвай, фаникулер, тролейбус), контролер билетов"},
                        new String[] {"Гид (зоопарк, музей, художественная галерея и пр.), гид-переводчик, туристический гид, экскурсовод"},
                        new String[] {"Бортпроводница (на земле), Ресепшионист гостиницы, Секретарь стоматологии, Специалист Call-центра"},
                        new String[] {"Секретарь широкого профиля, Секретарь кабинета, Стенографист"},
                        new String[] {"Системный администратор, Архитектор информационных систем, IT-проповедник"},
                        new String[] {"Функциональный руководитель по финансовой, административной и юридической деятельности, Главный аудитор"},
                        new String[] {"Кассир казино, Кассир на станции обслуживания, Кассир по выдаче билетов (развлекательные и спортивные мероприятия), Контролер-кассир"},
                        new String[] {"Диспетчер по флоту, Диспетчер порта, Портовый эколог, Системный инженер морской"},
                        new String[] {"Диспетчер автомобильного транспорта, Диспетчер вагонного депо, Диспетчер по регулированию вагонного парка"},
                        new String[] {"Инженер-суперинтендант, морской, Капитан судна"},
                        new String[] {"Директор гостиницы (гостиничного учреждения), Директор отеля, Руководитель студенческого общежития, Трактирщик (собственник)"},
                        new String[] {"Директор (управляющий) вагона-ресторана, Директор кафе, Заведующий залом (ресторана, кафе и др.), Заведующий столовой"},
                        new String[] {"Директор выставочного центра, Заведующий парикмахерской, Руководитель туристического агентства"},
                        new String[] {"Главный инженер (на транспорте), Заведующий ремонтно-механической мастерской, Начальник вокзала"},
                        new String[] {"Бренд-шеф, Су-шеф, Шеф-кондитер, Шеф-повар, Шеф-повар по солениям, Шеф-повар по соусам"},
                        new String[] {"Дежурный по депо подвижного состава, Дежурный по сопровождению воздушных судов, Дежурный по станции метрополитена"},
                        new String[] {"Репортажный фотограф, Спортивный фотограф, Фотограф, Фотограф портретный, Художник-фотограф"},
                        new String[] {"Повар (школьный, кафетерия, лечебного учреждения), повар на банкете, соусье"},
                        new String[] {"Путешествия"}
                ).resizeKeyboard(true));

                this.execute(response);
                break;
            }

            case "Агент по туризму, оператор по бронированию билетов (авиа, жд и пр.), сотрудник выдачи билетов": {
                lite(chatId,"Требования: работа с клиентами в рамках тура, осуществление информирования клиентов по всем возникшим вопроса, разработка маршрутов тура, формирования тура клиенту, бронирование транспорта, жилья, бронирование бителов, осуществление поддержки клиента в случае возникновения проблем во время тура, страхование клиента", "Организационная поддержка - 10");
                break;
            }

            case "Бортпроводник, Проводник, Стюард, Стюардесса": {
                lite(chatId,"Требования: осуществление проверки билетов у пассажира, объяснение правил техники безопастности, проведение инструктажа с пассажирами по правилам передвижения, по правилам действия при возникновении черезвычайных ситуация, осуществление контроля соверщения правонарушений, преступлений и их пресечение, предоставление услуг пассажирам (питание, постельное белье и пр.), остуществление порядка при посадке и высадке пассажиров, оказание медицинской помощи пассажирам, обеспечение сохранности хозяйственного имущества транспортного средства", "Организационная поддержка - 10");
                break;
            }

            case "Кондуктор (автобус, трамвай, фаникулер, тролейбус), контролер билетов": {
                lite(chatId,"Требования: осуществление сбора платы и выдачи билетов пассажирам, оказание помощи при посадке пассажиров, информирование пассажиров по маршруту транспортного средства, передача полученной выручки в кассу, оказание медицинской помощи пассажирам, обеспечение сохранности хозяйственного имущества транспортного средства, осуществление контроля соверщения правонарушений, преступлений и их пресечение, составление отчета о поступивших за смену денежных средств", "Организационная поддержка - 10");
                break;
            }

            case "Гид (зоопарк, музей, художественная галерея и пр.), гид-переводчик, туристический гид, экскурсовод": {
                lite(chatId,"Требования: сопровождение туристов по программе посещения объекта, разработка маршрута программы ( осуществление сопровождения туристов по имеющейся программе), показ доспреминичательностей объектов посещений, встреча поситителей и осуществление их регистрации, владение информации по культурному объекту (изучение информации по культурному объекту), осуществление перевода (письменного, устного) информации на территории объекта посещения, обеспечение безопастности поситителей на территории объекта посещения, обеспечение транспортировки поситителей", "Организационная поддержка - 10");
                break;
            }

            case "Бортпроводница (на земле), Ресепшионист гостиницы, Секретарь стоматологии, Специалист Call-центра": {
                lite(chatId,"Требования: регистрация клиентов; информирование; работа с жалобами; прием платежей; подготовка документов; обработка текста; редактирование документов; секретарские услуги", "Организационная поддержка - 10");
                break;
            }

            case "Секретарь широкого профиля, Секретарь кабинета, Стенографист": {
                lite(chatId,"Требования: печать документов; работа с программами; хранение документов; работа с почтой; подготовка документов; обработка текста; редактирование документов; секретарские услуги", "Организационная поддержка - 10");
                break;
            }

            case "Системный администратор, Архитектор информационных систем, IT-проповедник": {
                lite(chatId,"Требования: администрирование сетей; диагностика проблем; аварийное восстановление; работа с программным обеспечением; разработка компьютерных систем; управление данными; техническая поддержка; настройка компьютеров", "Организационная поддержка - 10");
                break;
            }

            case "Функциональный руководитель по финансовой, административной и юридической деятельности, Главный аудитор": {
                lite(chatId,"Требования: управление организацией; контроль бюджета; подбор персонала; взаимодействие с учреждениями; оперативный контроль; анализ счетов; оформление сделок; подготовка деклараций", "Организационная поддержка - 10");
                break;
            }

            case "Кассир казино, Кассир на станции обслуживания, Кассир по выдаче билетов (развлекательные и спортивные мероприятия), Контролер-кассир": {
                lite(chatId,"Требования: получение оплаты; выдача билетов; работа с кассой; регистрация цен; проверка оплаты; продажа билетов; информирование; проведение ярмарок", "Организационная поддержка - 10");
                break;
            }

            case "Диспетчер по флоту, Диспетчер порта, Портовый эколог, Системный инженер морской": {
                lite(chatId,"Требования: управление воздушными судами; утверждение планов полетов; информирование экипажей; решение проблем; пассажирские перевозки по воздуху; чартерные перевозки; туристические полеты; аэроклубы", "Организационная поддержка - 10");
                break;
            }

            case "Диспетчер автомобильного транспорта, Диспетчер вагонного депо, Диспетчер по регулированию вагонного парка": {
                lite(chatId,"Требования: ㅤㅤㅤㅤㅤㅤㅤㅤНКЗ ㅤㅤㅤㅤㅤㅤㅤㅤㅤㅤㅤㅤ1)предоставление информации; 2)контроль расстановки судов; 3)определение направления движения судов; 4)надзор за операциями. ㅤㅤㅤㅤㅤㅤМСОК 4 ㅤㅤㅤㅤㅤ1)пассажирские перевозки по водным путям; 2)пассажирские перевозки по рекам и каналам; 3)пассажирские перевозки по морю; 4)работа круизных судов", "Организационная поддержка - 10");
                break;
            }

            case "Инженер-суперинтендант, морской, Капитан судна": {
                lite(chatId,"Требования: контроль работы водителей; 2)обеспечение безопасности движения; инструктажи для водителей; руководство перевозками; работа диспетчеров; автомобильные перевозки; чартерные перевозки; перевозка пассажиров по суше", "Организационная поддержка - 10");
                break;
            }

            case "Директор гостиницы (гостиничного учреждения), Директор отеля, Руководитель студенческого общежития, Трактирщик (собственник)": {
                lite(chatId,"Требования: управление судами; контроль работы оборудования; применение знаний; надзор за ремонтом судов; пассажирские перевозки по водным путям; пассажирские перевозки по рекам и каналам; пассажирские перевозки по морю; работа круизных судов", "Организационная поддержка - 10");
                break;
            }

            case "Директор (управляющий) вагона-ресторана, Директор кафе, Заведующий залом (ресторана, кафе и др.), Заведующий столовой": {
                lite(chatId,"Требования: руководство ведением хозяйства; надзор за мерами безопасности; управление барами и ресторанами; соблюдение законов; управление заведением; организация деятельности; принятие рещений; оперативный контроль", "Организационная поддержка - 10");
                break;
            }

            case "Директор выставочного центра, Заведующий парикмахерской, Руководитель туристического агентства": {
                lite(chatId,"Требования: руководство учреждением; руководство персоналом; планирование бюджета; подбор персонала; управление учреждением; организация деятельности; принятие рещений; оперативный контроль", "Организационная поддержка - 10");
                break;
            }

            case "Главный инженер (на транспорте), Заведующий ремонтно-механической мастерской, Начальник вокзала": {
                lite(chatId,"Требования: транспортные услуги; обновление подвижного состава; заключение договоров; подбор персонала; управление компанией; организация деятельности; принятие рещений; оперативный контроль", "Организационная поддержка - 10");
                break;
            }

            case "Бренд-шеф, Су-шеф, Шеф-кондитер, Шеф-повар, Шеф-повар по солениям, Шеф-повар по соусам": {
                lite(chatId,"Требования: разработка меню; контроль качества блюд; приготовление пищи; руководство работниками; работа ресторанов; предоставление питания; быстрое обслуживание; выездное обслуживание", "Организационная поддержка - 10");
                break;
            }

            case "Дежурный по депо подвижного состава, Дежурный по сопровождению воздушных судов, Дежурный по станции метрополитена": {
                lite(chatId,"Требования: учет использования транспорта; управление маршрутами; оформление документов; планирование движения; пассажирские перевозки по воздуху; чартерные перевозки; туристические полеты; пассажирские перевозки по морю", "Организационная поддержка - 10");
                break;
            }

            case "Репортажный фотограф, Спортивный фотограф, Фотограф, Фотограф портретный, Художник-фотограф": {
                lite(chatId,"Требования: подготовка фотографий; изготовление портретов; изучение требований; настройка оборудования; фотопроизводство; портретная фотография; проявление пленки; ретуширование фотографий", "Организационная поддержка - 10");
                break;
            }

            case "Повар (школьный, кафетерия, лечебного учреждения), повар на банкете, соусье": {
                lite(chatId,"Требования: планирование меню; руководство помощниками; смешивание ингредиентов; уборка кухни; работа ресторанов; предоставление питания; быстрое обслуживание; выездное обслуживание", "Организационная поддержка - 10");
                break;
            }

            case "Помощь по хозяйству - 7": {
                var response = new SendMessage(chatId, "Вы выбрали Помощь по хозяйству");

                response.replyMarkup(new ReplyKeyboardMarkup(
                        new String[] {"Повар, повар на банкете, соусье"},
                        new String[] {"Бариста, Бармен, Буфетчик, Официант"},
                        new String[] {"Изготовитель пиццы, Кухонный рабочий"},
                        new String[] {"Аудитор, Бухгалтер, Бухгалтер по налогообложению, Бухгалтер-ревизор, Координатор по бюджету"},
                        new String[] {"Адвокат, Аналитик по вопросам интеллектуальной собственности, Нотариус, Медиатор"},
                        new String[] {"Менеджер по летным стандартам, Штурман (на флоте)"},
                        new String[] {"Бортинженер (бортмеханик), Летчик, Летчик-инструктор, Пилот вертолета, Пилот самолета"},
                        new String[] {"Водитель автобуса, Водитель машины скорой помощи, Водитель междугороднего автобуса, Водитель троллейбуса"},
                        new String[] {"Водитель погрузчика, Крановщик портовый, Машинист крана (крановщик), Машинист подъемной машины, Оператор фуникулера"},
                        new String[] {"Боцман, Матрос, Рулевой, Смотритель маяка, Шкипер"},
                        new String[] {"Фельдшер общей практики, Санитарный фельдшер"},
                        new String[] {"Массажист, Медицинская(ий) сестра/брат, Медицинская(ий) сестра/брат (специализированная(ый), Медицинская(ий) сестра/брат по уходу"},
                        new String[] {"Гравер оригиналов топографических карт, Каротажник, Моторист электроразведочной станции"},
                        new String[] {"Горничная, Уборщик гостиничных помещений"},
                        new String[] {"Велорикша, Водитель транспортного средства с педальным управлением (велотранспорт), Возчик, Рикша"},
                        new String[] {"Железнодорожный грузчик, Комплектовщик товаров, Носильщик на складе, Сортировщик багажа, Оператор кросс-логистики"},
                        new String[] {"Путешествия"}
                ).resizeKeyboard(true));

                this.execute(response);
                break;
            }

            case "Повар, повар на банкете, соусье": {
                lite(chatId,"Требования: планирование меню; руководство помощниками; смешивание ингредиентов; уборка кухни; работа ресторанов; предоставление питания; быстрое обслуживание; выездное обслуживание", "Помощь по хозяйству - 7");
                break;
            }

            case "Бариста, Бармен, Буфетчик, Официант": {
                lite(chatId,"Требования: приготовление напитков; накрытие столов; подача меню; прием оплаты; работа ресторанов; быстрое обслуживание; подача напитков; работа заведений с подачей напитков", "Помощь по хозяйству - 7");
                break;
            }

            case "Изготовитель пиццы, Кухонный рабочий": {
                lite(chatId,"Требования: приготовление простых блюд; нарезка продуктов; принятие заказов; приём ингредиентов; предоставление питания; доставка еды; общественное питание; работа столовых и буфетов", "Помощь по хозяйству - 7");
                break;
            }

            case "Аудитор, Бухгалтер, Бухгалтер по налогообложению, Бухгалтер-ревизор, Координатор по бюджету": {
                lite(chatId,"Требования: бухгалтерский контроль; подготовка финансовых отчетов; отчёты по доходам; консультирование руководства; подготовка счетов; анализ счетов; подготовка деклараций; консультации", "Помощь по хозяйству - 7");
                break;
            }

            case "Адвокат, Аналитик по вопросам интеллектуальной собственности, Нотариус, Медиатор": {
                lite(chatId,"Требования: проведение слушаний; определение прав и обязанностей сторон; вынесение решений; объявление приговора; представление интересов сторон; рассмотрение дел; подготовка документов; юридическая работа", "Помощь по хозяйству - 7");
                break;
            }

            case "Менеджер по летным стандартам, Штурман (на флоте)": {
                lite(chatId,"Требования: планирование рейсов; утверждение плана полета; управление судами; ведение журналов о рейсах и полетах; пассажирские перевозки по воздуху; чартерные перевозки; туристические полеты; аэроклубы", "Помощь по хозяйству - 7");
                break;
            }

            case "Бортинженер (бортмеханик), Летчик, Летчик-инструктор, Пилот вертолета, Пилот самолета": {
                lite(chatId,"Требования: управление воздушным судном; контроль безопасности полета; проведение проверок; проведение инструктажей; пассажирские перевозки по воздуху; чартерные перевозки; туристические полеты; аренда воздушного транспорта", "Помощь по хозяйству - 7");
                break;
            }

            case "Водитель автобуса, Водитель машины скорой помощи, Водитель междугороднего автобуса, Водитель троллейбуса": {
                lite(chatId,"Требования: вождение транспорта; 2)открытие и закрытие дверей; помощь пассажирам с багажом; обеспечение безопасности; перевозка по суше; работа транспорта; перевозки на дальние расстояния; перевозки по маршрутам", "Помощь по хозяйству - 7");
                break;
            }

            case "Водитель погрузчика, Крановщик портовый, Машинист крана (крановщик), Машинист подъемной машины, Оператор фуникулера": {
                lite(chatId,"Требования: управление механизмами для подъёма и спуска; проверка оборудования на износ; техническое обслуживание механизмов; учёт выхода из строя транспорта. движение фуникулеров и канатных дорог; грузовые перевозки; перевозка крупных грузов; аренда грузовых автомобилей", "Помощь по хозяйству - 7");
                break;
            }

            case "Боцман, Матрос, Рулевой, Смотритель маяка, Шкипер": {
                lite(chatId,"Требования: наблюдение за морем; управление судном; обслуживание судов; уборка; пассажирские перевозки по водным путям; пассажирские перевозки по рекам и каналам; пассажирские перевозки по морю; работа круизных судов", "Помощь по хозяйству - 7");
                break;
            }

            case "Фельдшер общей практики, Санитарный фельдшер": {
                lite(chatId,"Требования: медицинские процедуры; доврачебная помощь; санитарный надзор; ведение документации; работа больниц; здравоохранение; неотложная помощь; медицинский осмотр", "Помощь по хозяйству - 7");
                break;
            }

            case "Массажист, Медицинская(ий) сестра/брат, Медицинская(ий) сестра/брат (специализированная(ый), Медицинская(ий) сестра/брат по уходу": {
                lite(chatId,"Требования: проведение процедур; уход за пациентами; введение лекарств; предоставление помощи. медицинский осмотр; медицинские услуги; охрана здоровья; парамедицинская деятельность", "Помощь по хозяйству - 7");
                break;
            }

            case "Гравер оригиналов топографических карт, Каротажник, Моторист электроразведочной станции": {
                lite(chatId,"Требования: изготовление карт; наклейка названий; ремонт знаков; техническое обслуживание; картографическая деятельность; разведка; установление границ участков; составление чертежей", "Помощь по хозяйству - 7");
                break;
            }

            case "Горничная, Уборщик гостиничных помещений": {
                lite(chatId,"Требования: поддержание чистоты; уборка номеров; обеспечение бельем;использование моющих средств; общая уборка; специализированная уборка; уборка жилых домов; уборка внутренних помещений", "Помощь по хозяйству - 7");
                break;
            }

            case "Велорикша, Водитель транспортного средства с педальным управлением (велотранспорт), Возчик, Рикша": {
                lite(chatId,"Требования: погрузка товаров; проверка на износа; техническое обслуживание; сбор платы за проезд; работа пассажирского транспорта; пассажирские перевозки на животной тяге; аренда частных автомобилей с водителем; перевозки на дальние расстояния", "Помощь по хозяйству - 7");
                break;
            }

            case "Железнодорожный грузчик, Комплектовщик товаров, Носильщик на складе, Сортировщик багажа, Оператор кросс-логистики": {
                lite(chatId,"Требования: упаковка мебели; перенос товаров; погрузка и разгрузка; складирование товаров; услуги носильщиков; перевозка; работа терминалов; работа на железных дорогах", "Помощь по хозяйству - 7");
                break;
            }

            case "Поддержка знаний - 4": {
                var response = new SendMessage(chatId, "Вы выбрали Поддержка знаний");

                response.replyMarkup(new ReplyKeyboardMarkup(
                        new String[] {"Инструктор по аэробике, Инструктор по альпинизму и скалолазанию, Инструктор по дайвингу, Инструктор по плаванию"},
                        new String[] {"Координатор по обучению летного состава, Координатор службы бортпроводников, Контролер службы пассажирского автомобильного транспорта"},
                        new String[] {"Путешествия"}
                ).resizeKeyboard(true));

                this.execute(response);
                break;
            }

            case "Инструктор по аэробике, Инструктор по альпинизму и скалолазанию, Инструктор по дайвингу, Инструктор по плаванию": {
                lite(chatId,"Требования: оценка способностей; обучение движениям; работа с оборудованием; техника безопасности; услуги тренеров; спортивное обучение; обучение плаванию; обучение на базах", "Поддержка знаний - 4");
                break;
            }

            case "Координатор по обучению летного состава, Координатор службы бортпроводников, Контролер службы пассажирского автомобильного транспорта": {
                lite(chatId,"Требования: использование оборудования; определение обязанностей; решение проблем; контролирование работников; перевозка пассажиров воздушным транспортом; диспетчерские службы; перевозка пассажиров по суше; перевозки по маршрутам", "Поддержка знаний - 4");
                break;
            }

            case "Обеспечение безопасности - 3": {
                var response = new SendMessage(chatId, "Вы выбрали Обеспечение безопасности");

                response.replyMarkup(new ReplyKeyboardMarkup(
                        new String[] {"Матрос-спасатель, Спасатель в горах, Спасатель-водолаз"},
                        new String[] {"Агент досмотра, сотрудник транспортной безопастности"},
                        new String[] {"Охранник, сотрудник (оператор) службы внутренней безопасности, инкассатор"},
                        new String[] {"Техник авиационной безопасности, Техник по обработке полетной информации, Техник по радиолокации"},
                        new String[] {"Путешествия"}
                ).resizeKeyboard(true));

                this.execute(response);
                break;
            }

            case "Матрос-спасатель, Спасатель в горах, Спасатель-водолаз": {
                lite(chatId,"Требования: патрулирование пляжей; поиск пострадавших; доврачебная помощь; объяснение правил поведения. спасение людей и животных; оказание помощи; обеспечение запасов для использования; береговая охрана", "Обеспечение безопасности - 3");
                break;
            }

            case "Агент досмотра, сотрудник транспортной безопастности": {
                lite(chatId,"Требования: производит обеспечение безопастности на транспорте; проводит досмотр пассажиров, членов экипажей, обслуживающего персонала, ручной клади, багажа, груза, почты, транспорта; изымает и оформляет в установленном порядке изъятие запрещенных к перевозке опасных предметов и веществ; применяет технические средства и утвержденную технологию досмотра для обеспечения безопасности; анализ и оценка рисков - защита объектов транспортного хозяйства; разработка мер по профилактике и предотвращению актов незаконного вмешательства; составляет донесения о происшествиях; ведет установленную отчетную документацию\n", "Обеспечение безопасности - 3");
                break;
            }

            case "Охранник, сотрудник (оператор) службы внутренней безопасности, инкассатор": {
                lite(chatId,"Требования: осуществление охраны и патрулирования помещений подотчетной территории; осуществление выдачи пропусков на объекты; охранение порядка на подконтрольной территории; обеспечение пожарной безопастности; защита граждан от возникающих жизни и здоровью угроз; охрана и учет материальнх ценностей на подконтрольной территории; предоставление услуг телохранителей; инкассация и доставка денег", "Обеспечение безопасности - 3");
                break;
            }

            case "Техник авиационной безопасности, Техник по обработке полетной информации, Техник по радиолокации": {
                lite(chatId,"Требования: техническая помощь; разработка спецификаций; обеспечение безопасности движения; контроль оборудования; пассажирские перевозки по воздуху; чартерные перевозки; туристические полеты; аэроклубы", "Обеспечение безопасности - 3");
                break;
            }

            case "Инновации": {
                var response = new SendMessage(chatId, "Вы выбрали Инновации");

                response.replyMarkup(new ReplyKeyboardMarkup(
                        new String[] {"Исследования"},
                        new String[] {"Дизайн"},
                        new String[] {"Проектирование"},
                        new String[] {"IT и информационная безопасность"},
                        new String[] {"Творчество"},
                        new String[] {"Организация"},
                        new String[] {"Спорт"}
                ).resizeKeyboard(true));

                this.execute(response);
                break;
            }

            case "Исследования": {
                var response = new SendMessage(chatId, "Исследования");

                response.replyMarkup(new ReplyKeyboardMarkup(
                        new String[] {"Производство знаний"},
                        new String[] {"Хранение знаний"},
                        new String[] {"Инновации"}
                ).resizeKeyboard(true));

                this.execute(response);
                break;
            }

            case "Производство знаний": {
                var response = new SendMessage(chatId, "Производство знаний");

                response.replyMarkup(new ReplyKeyboardMarkup(
                        new String[] {"Астроном"},
                        new String[] {"Медицинский физик"},
                        new String[] {"Физик-ядерщик"},
                        new String[] {"Климатолог; Метеоролог; Синоптик"},
                        new String[] {"Химик (общий профиль); Научный сотрудник (в области химии)"},
                        new String[] {"Геолог; Геологоразведчик; Геофизик; Гидрогеолог; Минеролог"},
                        new String[] {"Математик; Актуарий; Статистик"},
                        new String[] {"Анатом; Бактериолог; Биолог; Ботаник; Зоолог; Микробиолог; Фармаколог; Биотехнолог"},
                        new String[] {"Агроном; Агрохимик; Агроном-дендролог; Инженер лесного хозяйства; Почвовед"},
                        new String[] {"Эколог; Аналитик, загрязнение атмосферы; Аналитик, исследования качества воды; Специалист в области защиты окружающей среды"},
                        new String[] {"Антрополог; Археолог; Социолог; Этнолог"},
                        new String[] {"Философ; Культуролог"},
                        new String[] {"Писатель; Редактор книг; Составитель технических текстов; Составитель указателей"},
                        new String[] {"Преподаватель философии, ВУЗ Преподаватель, доцент, профессор культурологии, ВУЗ"},
                        new String[] {"Преподаватель математики, ВУЗ Преподаватель, доцент, профессор статистики, ВУЗ"},
                        new String[] {"Преподаватель физики, ВУЗ; Преподаватель химии, ВУЗ; Преподаватель биологии, ВУЗ"},
                        new String[] {"Преподаватель агрономии, ВУЗ; Преподаватель животноводства, ВУЗ; Преподаватель растениеводства"},
                        new String[] {"Преподаватель архитектуры, ВУЗ; Преподаватель по электроэнергетике, ВУЗ"},
                        new String[] {"Преподаватель клинической медицины, ВУЗ; Преподаватель фармакологии, ВУЗ"},
                        new String[] {"Врач акушер-гинеколог; Врач-вирусолог; Врач-невролог ; Врач-психиатр; Врач-пульмонолог; Врач-терапевт; Врач-эндокринолог"},
                        new String[] {"Врач-кардиохирург; Врач-нейрохирург; Врач-хирург; Хирург-трансплантолог"},
                        new String[] {"Врач-рентгенолог; Врач-томограф; Врач лучевой диагностики; Врач ультразвуковой диагностики"},
                        new String[] {"Клинический фармацевт ; Фармацевт"},
                        new String[] {"Аэрофотогеодезист; Гидрограф; Инженер по землеустройству; Инженер по кадастру; Картограф; Топограф"},
                        new String[] {"Исследования"}
                ).resizeKeyboard(true));

                this.execute(response);
                break;
            }

            case "Астроном": {
                lite(chatId,"Требования: проведение астрономических исследований; составление научной документации, отчетов; разработка приборов для астрономических наблюдений и обработки данных; изучение влияния космического излучения на жизнь на Земле; совершенствование или разработка концепций, теорий, объясняющих устройство вселенной; применение астрономических данных в навигации, авиации, космонавтике, геодезии, картографии; наблюдение и анализ небесных явлений.", "Производство знаний");
                break;
            }

            case "Медицинский физик": {
                lite(chatId,"Требования: проведение исследований в области физики; составление научной документации, отчетов; изучение воздействия радиационного излучения на человека и окружающую среду; разработка диагностических методов и приборов; введение в эксплуатацию и обслуживание медицинского оборудования; применение физических знаний в медицинской сфере; измерение доз, переносимых пациентами; лучевая терапия и диагностика.", "Производство знаний");
                break;
            }

            case "Физик-ядерщик": {
                lite(chatId,"Требования: проведение исследований в области ядерной физики; составление научной документации, отчетов; изучение влияния радиации на природу и человека; проведение исследований и контроль над атомными реакторами; разработка ядерных энергетических установок военного назначения; дозиметрические измерения; снятие показаний с различных приборов; обработка получаемых данных.", "Производство знаний");
                break;
            }

            case "Климатолог; Метеоролог; Синоптик": {
                lite(chatId,"Требования: исследование изменений в климате Земли; исследование различных климатических явлений; изучение данных, полученных на метеостанциях, со спутников; прогнозирование погоды; изучение влияния погоды на окружающую среду; проведение опытов по управлению погодой; анализ воздействия человека на климат; составление научной документации, отчетов.", "Производство знаний");
                break;
            }

            case "Химик (общий профиль); Научный сотрудник (в области химии)": {
                lite(chatId,"Требования: изучение химического строения различных веществ; химический контроль процессов на производстве; анализ химического строения различных материалов; проведение опытов для изучения химических изменений в различных веществах; химический анализ воздуха, воды и т.д.; разработка новых материалов; воспроизведение существующих и создание новых искусственных веществ.", "Производство знаний");
                break;
            }

            case "Геолог; Геологоразведчик; Геофизик; Гидрогеолог; Минеролог": {
                lite(chatId,"Требования: изучение состава и структуры земной коры; исследование горных пород, минералов, ископаемых остатков; исследование свойств грунтовых и поверхностных вод; анализ и прогнозирование вулканической и тектонической деятельности Земли; изучение и реконструкция геологической истории Земли; подготовка геологических отчетов, карт, графиков и диаграмм.; исследование сейсмических, гравитационных, электрических, термических и магнитных сил Земли; поиск и разведка месторождений полезных ископаемых;", "Производство знаний");
                break;
            }

            case "Математик; Актуарий; Статистик": {
                lite(chatId,"Требования: фундаментальные математические исследования; применение математических моделей и методов в практике страхования; применение математических моделей и методов для обработки данных исследований; разработка математических и статистических методов в гуманитарных и естественных науках; математическое исследование рисков; логический анализ эффективности деятельности предприятия; математическое прогнозирование финансовых последствий деятельности; обработка, анализ, публикация статистических данных", "Производство знаний");
                break;
            }

            case "Анатом; Бактериолог; Биолог; Ботаник; Зоолог; Микробиолог; Фармаколог; Биотехнолог": {
                lite(chatId,"Требования: классификация и мониторинг живых организмов; исследования биологических процессов для применения в медицине, сельском хозяйстве, фармакологии и т.д.; генные исследования; исследование болезнетворных организмов; изучение и построение теорий, объясняющих происхождение и развитие жизни; изучение живых организмов в лабораторных и полевых условиях; оценка влияния человека на окружающую среду; подготовка научной документации.", "Производство знаний");
                break;
            }

            case "Агроном; Агрохимик; Агроном-дендролог; Инженер лесного хозяйства; Почвовед": {
                lite(chatId,"Требования: проведение научных исследований в области ботаники, химии, зоологии, агрономии и т.д.; изучение факторов, влияющих на рост растений и животных в сельском хозяйстве; исследование факторов, влияющих на рост и здоровье лесных пород; разработка способов повышения эффективности сельского хозяйства; исследование плодородия почвы; исследование и разработка методов борьбы с вредителями; исследование и разработка методов борьбы с пожарами, эрозией почвы, засухами и другими катаклизмами.", "Производство знаний");
                break;
            }

            case "Эколог; Аналитик, загрязнение атмосферы; Аналитик, исследования качества воды; Специалист в области защиты окружающей среды": {
                lite(chatId,"Требования: проведение научных исследований в области экологии; исследование загрязнений окружающей среды; химический анализ воздуха, воды, почвы и т.д. для выявления загрязнений; разработка методов восстановления окружающей среды; исследование причин экологических проблем; оценка вреда для окружающей среды разных видов деятельности; разработка оптимальных способов решения экологических проблем; разработка планов деятельности по защите окружающей среды.", "Производство знаний");
                break;
            }

            case "Антрополог; Археолог; Социолог; Этнолог": {
                lite(chatId,"Требования: исследование наиболее общих проблем, касающихся происхождения и развития общества; исследование существующих законов общественной жизни; использование данных других наук в исследованиях социальных систем; участие в разработке учебных курсов по общественным наукам; исследование и реконструкция социальных норм и ценностей; изучение истории культурных и социальных институтов; изучение истории человечества по вещественным источникам; разработка теорий, моделей и методов объяснения социальных явлений.", "Производство знаний");
                break;
            }

            case "Философ; Культуролог": {
                lite(chatId,"Требования: исследования наиболее общих проблем философии и теории культуры; философские и культурологические исследования повседневности; использование данных других наук в исследованиях человеческого опыта; участие в составлении программ учебных курсов; интерпретация и развитие философских концепций; изучение философских текстов; разработка теорий для объяснения человеческого опыта; написание статей, монографий.", "Производство знаний");
                break;
            }

            case "Писатель; Редактор книг; Составитель технических текстов; Составитель указателей": {
                lite(chatId,"Требования: написание подготовка к изданию научно-технической литературы; написание и подготовка к изданию монографий по общественным и гуманитарным наукам; написание и подготовка к изданию монографий по естественным наукам; написание и подготовка к изданию монографий по проблемам техники и технологий; написание и подготовка к изданию научно-популярных книг; проведение исследований по выбранной теме; подготовка справочного материала, документов и чертежей для издания книги; написание брошюр, путеводителей, справочников, указателей.", "Производство знаний");
                break;
            }

            case "Преподаватель философии, ВУЗ Преподаватель, доцент, профессор культурологии, ВУЗ": {
                lite(chatId,"Требования: разработка и изменение учебных программ; проведение учебных занятий; проведение научных исследований в гуманитарных и общественных науках; подготовка учебных изданий, пособий и статей; участие в научных конференциях и семинарах; побуждение студентов к дискуссиям и независимым размышлениям; проверка и оценка контрольных работ и экзаменов; руководство научной работы студентов.", "Производство знаний");
                break;
            }

            case "Преподаватель математики, ВУЗ Преподаватель, доцент, профессор статистики, ВУЗ": {
                lite(chatId,"Требования: разработка и изменение учебных программ; проведение учебных занятий; проведение научных исследований в области математики; подготовка учебных изданий, пособий и статей; участие в научных конференциях и семинарах; обучение студентов навыкам математического мышления; проверка и оценка контрольных работ и экзаменов; руководство научной работой студентов.", "Производство знаний");
                break;
            }

            case "Преподаватель физики, ВУЗ; Преподаватель химии, ВУЗ; Преподаватель биологии, ВУЗ": {
                lite(chatId,"Требования: разработка и изменение учебных программ; проведение учебных занятий; проведение естественнонаучных исследований; подготовка учебных изданий, пособий и статей; участие в научных конференциях и семинарах; привитие студентам навыков математического мышления; проверка и оценка контрольных работ и экзаменов; руководство научной работой студентов.", "Производство знаний");
                break;
            }

            case "Преподаватель агрономии, ВУЗ; Преподаватель животноводства, ВУЗ; Преподаватель растениеводства": {
                lite(chatId,"Требования: разработка и изменение учебных программ; проведение учебных занятий; проведение научных исследований в области сельского хозяйства и природопользования; подготовка учебных изданий, пособий и статей; участие в научных конференциях и семинарах; обучение студентов основным принципам сельскохозяйственной деятельности; проверка и оценка контрольных работ и экзаменов; руководство научной работой студентов.", "Производство знаний");
                break;
            }

            case "Преподаватель архитектуры, ВУЗ; Преподаватель по электроэнергетике, ВУЗ": {
                lite(chatId,"Требования: проведение учебных занятий; проведение научных исследований в области технических наук; подготовка учебных изданий, пособий и статей; участие в научных конференциях и семинарах; передача теоретических и практических знаний по определенной специальности; проверка и оценка контрольных работ и экзаменов; руководство научной работой студентов.", "Производство знаний");
                break;
            }

            case "Преподаватель клинической медицины, ВУЗ; Преподаватель фармакологии, ВУЗ": {
                lite(chatId,"Требования: разработка и изменение учебных программ; проведение учебных занятий; проведение научных исследований в области медицины; подготовка учебных изданий, пособий и статей; участие в научных конференциях и семинарах; формирование у студентов клинического мышления; проверка и оценка контрольных работ и экзаменов; руководство научной работой студентов.", "Производство знаний");
                break;
            }

            case "Врач акушер-гинеколог; Врач-вирусолог; Врач-невролог ; Врач-психиатр; Врач-пульмонолог; Врач-терапевт; Врач-эндокринолог": {
                lite(chatId,"Требования: проведение научных исследований в области медицины; генные исследования; исследование болезнетворных организмов; медицинский осмотр пациентов и постановка диагноза; назначение лечения; мониторинг реакции пациента на лечение; исследование клинических проявлений и распространения определенных болезней; разработка профилактических мер.", "Производство знаний");
                break;
            }

            case "Врач-кардиохирург; Врач-нейрохирург; Врач-хирург; Хирург-трансплантолог": {
                lite(chatId,"Требования: проведение научных исследований в области медицины; генные исследования; исследования в области выращивания тканей и органов; медицинский осмотр пациентов и постановка диагноза; проведение хирургических операций; мониторинг состояния пациента в постоперационный период; изучение нового оборудования для проведения операций; изучение и внедрение новых методов проведения операции.", "Производство знаний");
                break;
            }

            case "Врач-рентгенолог; Врач-томограф; Врач лучевой диагностики; Врач ультразвуковой диагностики": {
                lite(chatId,"Требования: проведение обследований для постановки диагноза; проведение научных исследований, написание статей по функциональной диагностике; исследования нарушений или заболеваний с применением современной медицинской аппаратуры; применение знания причин и проявлений основных заболеваний в диагностике; применение знания физических принципов работы медицинских приборов; определение методов диагностики; клиническая интерпретация полученных результатов; изучение новых методов, инструментов и приборов диагностики.", "Производство знаний");
                break;
            }

            case "Клинический фармацевт ; Фармацевт": {
                lite(chatId,"Требования: исследование свойств различных химических веществ; исследование свойств биологических препаратов; исследования особенностей действия лекарств на организм; разработка новых и совершенствование существующих лекарств; разработка и исследование новых технологий доставки лекарств в организм; консультирование врачей по вопросам применения лекарств; участие в совершенствовании технологии производства лекарств; испытание новых лекарств.", "Производство знаний");
                break;
            }

            case "Аэрофотогеодезист; Гидрограф; Инженер по землеустройству; Инженер по кадастру; Картограф; Топограф": {
                lite(chatId,"Требования: землемерная деятельность; определение границ земельных участков; исследование местности с водоемами; исследование грунта для строительства; сбор информации для картографирования; точное позиционирование различных объектов; проектирование, составление и пересмотр карт; совершенствование и разработка инструментов для обработки пространственной информации.", "Производство знаний");
                break;
            }

            case "Хранение знаний": {
                var response = new SendMessage(chatId, "Хранение знаний");

                response.replyMarkup(new ReplyKeyboardMarkup(
                        new String[] {"Эксперт по комплектованию музейного и выставочного фонда; Специалист по учету музейных предметов"},
                        new String[] {"Библиотекарь - 2"},
                        new String[] {"Аналитик баз данных; Архитектор ИТ-инфраструктуры; Инженер по сопровождению баз данных"},
                        new String[] {"Исследования"}
                ).resizeKeyboard(true));

                this.execute(response);
                break;
            }

            case "Эксперт по комплектованию музейного и выставочного фонда; Специалист по учету музейных предметов": {
                lite(chatId,"Требования: обеспечение общего руководства музеем, библиотекой, руководство научно-исследовательской работой в библиотеке, музее; руководство персоналом; планирование бюджета и учет расходов; составление общих отчетов о работе; составление коллекции предметов; составление и обслуживание собраний книг, периодики и других изданий религиозного содержания; пополнение коллекций книг, документов и материальных предметов.", "Хранение знаний");
                break;
            }

            case "Библиотекарь - 2": {
                lite(chatId,"Требования: составление коллекции предметов; организация хранения коллекции; пополнение коллекции; пополнение архива; составление каталогов и справочников по собранному материалу; организация экспозиций в музеях; подготовка научной документации и отчетов; поиск и подбор информации по запросам исследователей.", "Хранение знаний");
                break;
            }

            case "Аналитик баз данных; Архитектор ИТ-инфраструктуры; Инженер по сопровождению баз данных": {
                lite(chatId,"Требования: обслуживание собраний книг, периодических изданий; обслуживание собраний фильмов и аудиозаписей; анализ запросов читателей; рекомендации по приобретению книг; составление каталогов; выдача книг и др. материалов; обслуживание читального зала; поиск и подбор информации в материалах библиотеки.", "Хранение знаний");
                break;
            }

            case "Дизайн": {
                var response = new SendMessage(chatId, "Дизайн");

                response.replyMarkup(new ReplyKeyboardMarkup(
                        new String[] {"Создание дизайна"},
                        new String[] {"Инновации"}
                ).resizeKeyboard(true));

                this.execute(response);
                break;
            }

            case "Создание дизайна": {
                var response = new SendMessage(chatId, "Создание дизайна");

                response.replyMarkup(new ReplyKeyboardMarkup(
                        new String[] {"Дизайнер игрушек"},
                        new String[] {"Дизайнер промышленных изделий"},
                        new String[] {"Дизайнер ювелирных украшений"},
                        new String[] {"Модельер одежды"},
                        new String[] {"Художник по костюмам"},
                        new String[] {"Декоратор интерьера"},
                        new String[] {"Дизайнер интерьера"},
                        new String[] {"Ландшафтный дизайнер"},
                        new String[] {"Студийный дизайнер"},
                        new String[] {"Сценограф"},
                        new String[] {"Художник-декоратор"},
                        new String[] {"Дизайнер Web-сайтов."},
                        new String[] {"Дизайнер печатных изданий"},
                        new String[] {"Мультимедийный дизайнер, Графический дизайнер"},
                        new String[] {"Дизайнер компьютерных игр"},
                        new String[] {"Художник компьютерной графики"},
                        new String[] {"Художник по рекламе"},
                        new String[] {"Художник-мультипликатор"},
                        new String[] {"Преподаватель, доцент, профессор художественного рисунка, ВУЗ"},
                        new String[] {"Преподаватель, доцент, профессор веб-дизайна, ВУЗ"},
                        new String[] {"Преподаватель, доцент, профессор истории дизайна, ВУЗ"},
                        new String[] {"Преподаватель, доцент, профессор моделирования, ВУЗ"},
                        new String[] {"Дизайн"}
                ).resizeKeyboard(true));

                this.execute(response);
                break;
            }

            case "Дизайнер игрушек": {
                lite(chatId,"Требования: —производство уменьшенных (масштабных) моделей и аналогичных игровых моделей, электрических железных дорог, конструкторов и т. д.; − подготовка эскизов, иллюстраций, моделей и образцов игрушек; − описание и рекомендации для производства касательно функциональных и эстетических материалов, методов производства и отделки игрушек", "Создание дизайна");
                break;
            }

            case "Дизайнер промышленных изделий": {
                lite(chatId,"Требования: − подготовка эскизов, диаграмм, иллюстраций, планов, образцов и моделей для передачи концепций дизайна; − определение целей и ограничений, посредством консультаций с клиентами и заинтересованными лицами; − разработка концепций дизайна промышленных, коммерческих и потребительских товаров; − выбор, описание и рекомендации для производства касательно функциональных и эстетических материалов, методов производства и отделки промышленных изделий", "Создание дизайна");
                break;
            }

            case "Дизайнер ювелирных украшений": {
                lite(chatId,"Требования: − подготовка эскизов, диаграмм, иллюстраций, планов, образцов и моделей для передачи концепций дизайна; − определение целей и ограничений, посредством консультаций с клиентами и заинтересованными лицами; − разработка концепций дизайна одежды, тканей, промышленных, коммерческих и потребительских товаров и украшений; − описание и рекомендации для производства касательно функциональных и эстетических материалов, методов производства и отделки украшений", "Создание дизайна");
                break;
            }

            case "Модельер одежды": {
                lite(chatId,"Требования: − разработка концепций дизайна одежды, тканей; − выбор, описание и рекомендации для производства касательно функциональных и эстетических материалов, методов производства и отделки одежды; − подготовка эскизов и иллюстраций для передачи концепций дизайна одежды; − определение целей и ограничений, посредством консультаций с клиентами и заинтересованными лицами;", "Создание дизайна");
                break;
            }

            case "Художник по костюмам": {
                lite(chatId,"Требования: − подготовка эскизов и иллюстраций для передачи концепций дизайна костюма; − разработка концепций дизайна костюма; − определение целей и ограничений, посредством консультаций с режиссером и заинтересованными лицами;", "Создание дизайна");
                break;
            }

            case "Декоратор интерьера": {
                lite(chatId,"Требования: − исследование и анализ эстетических потребностей, а также требований эффективности; − подготовка эскизов, схем, иллюстраций и планов для обсуждения концепций дизайна; − проектирование и оформление витрин и других рекламных зон для продвижения продуктов и услуг на рынке.;− выбор, уточнение и рекомендации по функциональным и отделочным материалам", "Создание дизайна");
                break;
            }

            case "Дизайнер интерьера": {
                lite(chatId,"Требования: − исследование и анализ пространственных, функциональных и эстетических потребностей, а также требований эффективности и безопасности; − подготовка эскизов, схем, иллюстраций и планов для обсуждения концепций дизайна; − проектирование и оформление витрин и других рекламных зон для продвижения продуктов и услуг на рынке.; − выбор, уточнение и рекомендации по функциональным и отделочным материалам", "Создание дизайна");
                break;
            }

            case "Ландшафтный дизайнер": {
                lite(chatId,"Требования: − исследование и анализ пространственных, функциональных и эстетических потребностей, а также требований эффективности и безопасности; − подготовка эскизов, схем, иллюстраций и планов для обсуждения концепций дизайна; − проектирование и оформление витрин и других рекламных зон для продвижения продуктов и услуг на рынке.; − выбор, уточнение и рекомендации по функциональным и отделочным материалам", "Создание дизайна");
                break;
            }

            case "Студийный дизайнер": {
                lite(chatId,"Требования: − исследование и анализ пространственных, функциональных и эстетических потребностей, а также требований эффективности и безопасности; − подготовка эскизов, схем, иллюстраций и планов для обсуждения концепций дизайна; − проектирование и оформление витрин и других рекламных зон для продвижения продуктов и услуг на рынке; − выбор, уточнение и рекомендации по функциональным и отделочным материалам", "Создание дизайна");
                break;
            }

            case "Сценограф": {
                lite(chatId,"Требования: − исследование и анализ эстетических потребностей; − подготовка эскизов, схем, иллюстраций и планов для обсуждения концепций дизайна; − выбор, уточнение и рекомендации по функциональным и отделочным материалам; − определение целей и ограничений, посредством консультаций с режиссером и заинтересованными лицами;", "Создание дизайна");
                break;
            }

            case "Художник-декоратор": {
                lite(chatId,"Требования: − исследование и анализ эстетических потребностей, а также требований эффективности; − подготовка эскизов, схем, иллюстраций и планов для обсуждения концепций дизайна; − проектирование витрин и других рекламных зон для продвижения продуктов и услуг на рынке.", "Создание дизайна");
                break;
            }

            case "Дизайнер Web-сайтов.": {
                lite(chatId,"Требования: − разработка концепций дизайна Web-сайта; − определение целей и ограничений, посредством консультаций с клиентами и заинтересованными лицами; − проведение исследований и анализа функциональных требований к Web-сайту; − обсуждение дизайнерских решений с клиентами, руководством, торговым и производственным персоналом;", "Создание дизайна");
                break;
            }

            case "Дизайнер печатных изданий": {
                lite(chatId,"Требования: − разработка концепций дизайна печатных изданий; − определение целей и ограничений, посредством консультаций с клиентами и заинтересованными лицами; − проведение исследований и анализа функциональных требований к дизайну; − обсуждение дизайнерских решений с клиентами, руководством, торговым и производственным персоналом; − подготовка оригинал-макетов для передачи концепций дизайна;", "Создание дизайна");
                break;
            }

            case "Мультимедийный дизайнер, Графический дизайнер": {
                lite(chatId,"Требования: − разработка сложной графики и анимации; − создание двухмерных и трехмерных изображений, с использованием программ компьютерной анимации или моделирования; − описание и документация выбранного дизайна продукции; − проведение исследований и анализ функциональных требований; − разработка концепций дизайна передаваемых субъектов; − определение целей и ограничений, посредством консультаций с клиентами и заинтересованными лицами;", "Создание дизайна");
                break;
            }

            case "Дизайнер компьютерных игр": {
                lite(chatId,"Требования: − разработка концепций дизайна компьютерных игр; − разработка сложной графики и анимации; − создание двухмерных и трехмерных изображений, изображающий объекты в движении, с использованием программ компьютерной; анимации или моделирования; − определение целей, посредством консультаций с клиентами и заинтересованными лицами; − проведение исследований и анализ функциональных требований; − обсуждение дизайнерских решений с клиентами, руководством, торговым и производственным персоналом;", "Создание дизайна");
                break;
            }

            case "Художник компьютерной графики": {
                lite(chatId,"Требования: − разработка сложной графики и анимации; − создание двухмерных и трехмерных изображений, с использованием программ компьютерной анимации или моделирования; − определение целей и ограничений, посредством консультаций с клиентами и заинтересованными лицами; − проведение исследований и анализ функциональных требований; − обсуждение дизайнерских решений с клиентами, руководством, торговым и производственным персоналом;", "Создание дизайна");
                break;
            }

            case "Художник по рекламе": {
                lite(chatId,"Требования: − разработка концепций дизайна рекламы; − разработка сложной графики и анимации; − определение целей и ограничений, посредством консультаций с клиентами и заинтересованными лицами; − проведение исследований и анализ функциональных требований; − обсуждение дизайнерских решений с клиентами, руководством, торговым и производственным персоналом", "Создание дизайна");
                break;
            }

            case "Художник-мультипликатор": {
                lite(chatId,"Требования: − разработка сложной графики и анимации; − создание двухмерных и трехмерных изображений, изображающий объекты в движении, с использованием программ компьютерной анимации или моделирования; − определение целей, посредством консультаций с клиентами и заинтересованными лицами; − проведение исследований и анализ функциональных требований; − обсуждение решений с клиентами, руководством, торговым и производственным персоналом;", "Создание дизайна");
                break;
            }

            case "Преподаватель, доцент, профессор художественного рисунка, ВУЗ": {
                lite(chatId,"Требования: − разработка и изменение учебных программ и подготовка учебных курсов по художественному рисунку; − подготовка и чтение лекций и проведение учебных занятий, семинаров и лабораторных работ; − подготовка отчетов и ведение таких документов, как табели успеваемости студентов, табели посещаемости, и характеристик учебного процесса; − проведение, проверка и оценка контрольных работ и экзаменов; − руководство научными исследованиями студентов;", "Создание дизайна");
                break;
            }

            case "Преподаватель, доцент, профессор веб-дизайна, ВУЗ": {
                lite(chatId,"Требования: − разработка и изменение учебных программ и подготовка учебных курсов по веб-дизайну; − подготовка и чтение лекций и проведение учебных занятий, семинаров и лабораторных работ; − подготовка отчетов и ведение таких документов, как табели успеваемости студентов, табели посещаемости, и характеристик учебного процесса; − проведение, проверка и оценка контрольных работ и экзаменов; − руководство научными исследованиями студентов;", "Создание дизайна");
                break;
            }

            case "Преподаватель, доцент, профессор истории дизайна, ВУЗ": {
                lite(chatId,"Требования: − разработка и изменение учебных программ и подготовка учебных курсов по истории дизайна; − подготовка и чтение лекций и проведение учебных занятий, семинаров и лабораторных работ; − подготовка отчетов и ведение таких документов, как табели успеваемости студентов, табели посещаемости, и характеристик учебного процесса; − проведение, проверка и оценка контрольных работ и экзаменов; − руководство научными исследованиями студентов;", "Создание дизайна");
                break;
            }

            case "Преподаватель, доцент, профессор моделирования, ВУЗ": {
                lite(chatId,"Требования: − разработка и изменение учебных программ и подготовка учебных курсов по моделированию; − подготовка и чтение лекций и проведение учебных занятий, семинаров и лабораторных работ; − подготовка отчетов и ведение таких документов, как табели успеваемости студентов, табели посещаемости, и характеристик учебного процесса; − проведение, проверка и оценка контрольных работ и экзаменов; − руководство научными исследованиями студентов;", "Создание дизайна");
                break;
            }

            case "Проектирование": {
                var response = new SendMessage(chatId, "Проектирование");

                response.replyMarkup(new ReplyKeyboardMarkup(
                        new String[] {"Создание и развитие проектов"},
                        new String[] {"Инновации"}
                ).resizeKeyboard(true));

                this.execute(response);
                break;
            }

            case "Создание и развитие проектов": {
                var response = new SendMessage(chatId, "Создание и развитие проектов");

                response.replyMarkup(new ReplyKeyboardMarkup(
                        new String[] {"Архитектор зданий и сооружений."},
                        new String[] {"Проектировщик садово-парковых ансамблей."},
                        new String[] {"Проектировщик-градостроитель"},
                        new String[] {"Проектировщик городской инфраструктуры."},
                        new String[] {"Геодезист"},
                        new String[] {"Инженер – строитель"},
                        new String[] {"Инженер по гражданскому строительству"},
                        new String[] {"Инженер по контролю за загрязнением окружающей среды"},
                        new String[] {"Инженер по водоочистительным сооружениям"},
                        new String[] {"Инженер-конструктор"},
                        new String[] {"Инженер по бурению"},
                        new String[] {"Инженер по биомедицинскому оборудованию"},
                        new String[] {"Инженер по лазерному оборудованию"},
                        new String[] {"Инженер-электрик"},
                        new String[] {"Инженер-энергетик"},
                        new String[] {"Инженер электроник"},
                        new String[] {"Инженер-разработчик по телекоммуникациям"},
                        new String[] {"Техник"},
                        new String[] {"Электрик."},
                        new String[] {"Архитектор программного обеспечения"},
                        new String[] {"Разработчик программного обеспечения"},
                        new String[] {"Сетевой инженер."},
                        new String[] {"Техник-строитель"},
                        new String[] {"Техник-проектировщик зданий и сооружений"},
                        new String[] {"Техник по охране окружающей среды"},
                        new String[] {"Техник-механик (общий профиль)"},
                        new String[] {"Техник по очистке нефти"},
                        new String[] {"Механик по буровым, горным работам"},
                        new String[] {"Техник-энергетик; Техник-электрик"},
                        new String[] {"Техник-электроник"},
                        new String[] {"Техник авиационной безопасности"},
                        new String[] {"Техник по монтажу телекоммуникационного оборудования"},
                        new String[] {"Электрик-строитель-монтажник аппаратуры и кабелей низкого напряжения; Электромонтажник по освещению и осветительным сетям"},
                        new String[] {"Электромеханик; Электромонтер"},
                        new String[] {"Преподаватель, профессор информационных систем, ВУЗ; Преподаватель, доцент, профессор архитектуры"},
                        new String[] {"Проектирование"}
                ).resizeKeyboard(true));

                this.execute(response);
                break;
            }

            case "Архитектор зданий и сооружений.": {
                lite(chatId,"Требования: - предоставление услуг по разработке и проектированию садов и парков;- создание чертежей различной степени сложности и вариативности, для проведения строительных работ, подготовка проектной документации;- консультирование клиентов при разработке концепции строений и ландшафтных решений;- сбор информации о месте расположения объекта и коммунальных данных о географических и экологических особенностях, с целью подготовки рекомендаций касательно застройки и использования земельного участка;- осуществление планирования территорий;- составление смет о примерной стоимости работ, количества материалов, техники и оборудования;- формирование технико-экономических обоснований для проведения работ;- оказание услуг по управлению строительной деятельностью;- выполнение строительного контроля и проведение авторского надзора за ходом выполнения работа;", "Создание и развитие проектов");
                break;
            }

            case "Проектировщик садово-парковых ансамблей.": {
                lite(chatId,"Требования: - оказание консультативных услуг в области проектирования и строительства ;- проектирование городов, населенных пунктов, городских районов и кварталов под влиянием определенных целей и задач; - планирование и координация отвода земельных угодий под парки, школы, учреждения, аэропорты, шоссе и сопутствующие проекты, а также под участки для строительства деловых, промышленных и жилых зданий;- согласование и обсуждение своих проектов с представителями власти, различных коммунальных структур, заказчиками, инвесторами, местными жителями и т.д.- своевременное изменение проектов, как на этапах исследования территории, так и ведения строительства в зависимости от нужд заказчика или запросов различных государственных структур или общества;- составление смет о примерной стоимости работ, количества материалов, техники и оборудования;- формирование технико-экономических обоснований для проведения работ;- выполнение строительного контроля и проведение авторского надзора за ходом выполнения работа;", "Создание и развитие проектов");
                break;
            }

            case "Проектировщик-градостроитель": {
                lite(chatId,"Требования: - оказание консультативных услуг в области проектирования и строительства городской инфрастуктуры;- проектирование размещения тепло-, водо- и энергосетей; - проектирование дорожно-транспортной сети, формирование маршрутов движения общественного транспорта и безопасности движения;- согласование и обсуждение своих проектов с представителями власти, различных коммунальных структур, заказчиками, инвесторами, местными жителями и т.д.- своевременное изменение проектов, как на этапах исследования территории, так и ведения строительства в зависимости от нужд заказчика или запросов различных государственных структур или общества;- составление смет о примерной стоимости работ, количества материалов, техники и оборудования;- формирование технико-экономических обоснований для проведения работ;- выполнение строительного контроля и проведение авторского надзора за ходом выполнения работа;", "Создание и развитие проектов");
                break;
            }

            case "Проектировщик городской инфраструктуры.": {
                lite(chatId,"Требования: - проведение геодезических изыскательных работ по предварительному заказу;- определение местоположения объектов геодезической съёмки, их точное позиционирование и дальнейшая регистрация данных в цифровом формате;- обзор, измерение и описание участков земной поверхности;- составление, пересмотр и изменение карт;- исследование, разработка и систем геодезических и топографических измерений- проведение исследований на наличие изменений участков земной поверхности в связи с естественными или искусственными причинами;- создание и ведение картографо-геодезического фонда;- подготовка, обработка и сведение картографо-геодезической информации за разные временные промежутки для создания полного пакета информации об изменениях исследуемой территории;", "Создание и развитие проектов");
                break;
            }

            case "Геодезист": {
                lite(chatId,"Требования: - проведение инженерно-технического проектирования систем водоснабжения, расположения тепло и электросетей в зданиях и сооружениях;- применение инженерно-технических правил проектирования при строительстве гидросооружений, атомных электростанций, заводов, производственных линий, а также в области промышленного строительства;- управление строительством, выполнение строительного контроля;- проведение экспертизы существующей проектной документации и результатов инженерных изысканий;- планирование систем технологических процессов при ведении строительных работ;- понимание чертежей и планов, умение их читать и руководствоваться ими в процессе строительства;- осуществление надзора за строительством сооружений, систем водоснабжения, газоснабжения и транспортировки, а также за изготовлением, установкой, эксплуатацией и техническим обслуживанием оборудования, механизмов и установок;- разрешение проектных и эксплуатационных проблем в различных областях строительства посредством применения инженерных разработок;", "Создание и развитие проектов");
                break;
            }

            case "Инженер – строитель": {
                lite(chatId,"Требования: - проведение инженерно-технического проектирования систем водоснабжения, расположения тепло и электросетей в жилых и общественных зданиях и помещениях, дорог, транспортных развязок и различных коммуникаций;- применение инженерно-технических правил проектирования при строительстве объектов гражданского строительства;- управление строительством, выполнение строительного контроля;- проведение экспертизы существующей проектной документации и результатов инженерных изысканий;- планирование систем технологических процессов при ведении строительных работ;- понимание чертежей и планов, умение их читать и руководствоваться ими в процессе строительства;- осуществление надзора за строительством объектов гражданского строительства;- разрешение проектных и эксплуатационных проблем в различных областях строительства посредством применения инженерных разработок;", "Создание и развитие проектов");
                break;
            }

            case "Инженер по гражданскому строительству": {
                lite(chatId,"Требования: - консультирование в области промышленного производства, эксплуатации техники и инженерных разработок с целью снижения уровня загрязнения окружающей среды;- ремонт и эксплуатация очистительных систем;- осуществление мониторинга загрязнений окружающей среды, инспектирования промышленных объектов;- выполнение надзора за соблюдением экологических требований при строительстве и эксплуатации промышленных сооружений;- осуществление подготовки и согласования документов обосновывающих нормативы допустимых выбросов и сбросов загрязняющих веществ в окружающую среду;- наблюдение за разработкой систем контроля, управления и восстановления качества воды, воздуха или почвы;- осуществление сотрудничества с учеными специалистами по вопросам мониторинга загрязнения окружающей среды и получению новых методов для анализа окружающей среды;- создание и утверждение программ для оценки эксплуатационной эффективности, а также обеспечение соблюдения природоохранного законодательства;", "Создание и развитие проектов");
                break;
            }

            case "Инженер по контролю за загрязнением окружающей среды": {
                lite(chatId,"Требования: - консультирование в области промышленного производства по вопросам очистки водных ресурсов используемых в промышленных целях;- ремонт и эксплуатация очистительных систем;- осуществление мониторинга водных ресурсов на наличие загрязнений;- выполнение надзора за соблюдением экологических требований при строительстве и эксплуатации водоочистительных сооружений;- осуществление подготовки и согласования документов регламентирующих требования к водоочистительным сооружениям и система очистки;- разработка и участие в разработке систем контроля, управления и восстановления качества воды;- осуществление сотрудничества с учеными специалистами по вопросам мониторинга загрязнения окружающей среды и получению новых методов для анализа окружающей среды;- поиск и устранение неисправностей в работе водоочистительных сооружений и их дальнейшей эксплуатации;", "Создание и развитие проектов");
                break;
            }

            case "Инженер по водоочистительным сооружениям": {
                lite(chatId,"Требования: - использование в процессе требований заявленных заказчиком;- консультирование в области машиностроения, промышленных процессов и оборудования, проектирования, связанного со строительством инженерных сооружений и производстве электротехники;− консультирование и разработка двигателей внутреннего сгорания и других неэлектрических моторов и двигателей;- консультирование и разработка производственного оборудования, машин и инструментов для обрабатывающей промышленности, горных работ, строительства, сельского хозяйства и других промышленных целей; - проведение испытаний с использований макетов, моделей и имитацией необходимых природных явлений;- проведение технических испытаний, исследований, анализов;- соблюдение соответствия оборудования установленным требованиям, правилам эксплуатации и технического обслуживания;- определение контрольных параметров и технологических процессов для обеспечения эффективной эксплуатации и безопасности машин, механизмов, станков, моторов, двигателей, промышленных установок, оборудования или систем;- сертифицирование оборудования;", "Создание и развитие проектов");
                break;
            }

            case "Инженер-конструктор": {
                lite(chatId,"Требования: - осуществление работ по геологическому изучению земных недр;- осуществление мониторинга геологической среды в районе исследования;- проведение лабораторных исследований горных пород;- проведение - геофизические, геологические и сейсмологических изысканий для сбора информации о геологической карте района исследований и наличии полезных ископаемых;- проведение исследований в области бурения скважин, добычи, хранения и транспортировки нефти и газа;- определение участков для бурения, разработка способов контроля притока нефти или газа из скважин;- осуществление ремонта и эксплуатации буровых установок, выявление нарушений и осуществление контроля за работами по замене изношенных частей;- проектирование новых прогрессивных технологий и технических средств для бурения нефтяных и газовых скважин;", "Создание и развитие проектов");
                break;
            }

            case "Инженер по бурению": {
                lite(chatId,"Требования: - проведение инженерно-технического консультирования заказчиков в сфере производства медицинского и биомедицинского оборудования;- разработка и оценка таких биологических и медицинских систем и изделий, как искусственные органы, протезы и инструменты;- проектирование приборов, используемых в различных медицинских процедурах: магнитно-резонансная томография, приборы для автоматических инъекций, электрокардиографы, аппаратура для искусственной вентиляции легких и введению наркоза во время операций;- проектирование различных компонентов и заменяемых деталей для медицинского оборудования;- выявление возможных опасностей и создание документов регламентирующих правила эксплуатации;- проведение испытаний чистоты, прочности и анализ состава материалов используемых в производстве аппаратов;- проведение испытаний работы техники и выявление недоработок и недостатков;- участие в сертификации созданного оборудования, его демонстрация и защита от критики;", "Создание и развитие проектов");
                break;
            }

            case "Инженер по биомедицинскому оборудованию": {
                lite(chatId,"Требования: - проведение инженерно-технического консультирования заказчиков в сфере производства медицинского и биомедицинского оборудования;- оценка возможностей приборов по удалению татуажа, коррекции зрения, микрохирургии и т.д.;- проектирование компонентов таких оптических инструментов, как линзы, микроскопы, телескопы, лазеры, системы оптических дисков и прочего оборудование на основе свойств света;- проектирование заменяемых деталей для медицинского оборудования;- выявление возможных опасностей и создание документов регламентирующих правила эксплуатации;- проведение испытаний чистоты, прочности и анализ состава материалов используемых в производстве аппаратов;- проведение испытаний работы техники и выявление недоработок и недостатков;- участие в сертификации созданного оборудования, его демонстрация и защита от критики;", "Создание и развитие проектов");
                break;
            }

            case "Инженер по лазерному оборудованию": {
                lite(chatId,"Требования: - консультирование в сфере инженерно-технического проектирования производств, относящихся к электротехнике, электронной технике и технике безопасности;- проведение расчетов по энергосбережению и повышению энергетической эффективности;- проведение испытаний, исследований и сбор анализов целостных механических и электрических систем, энергетическое обследование;- участие в прокладке и ремонте электросетей в гражданских зданиях, промышленных объектах;- ведение надзор за осуществлением правильной эксплуатации электротехнического оборудования;- обеспечение работы и ремонт электрического оборудования;- консультирование и разработка систем для электрических двигателей, электрической тяги и другого оборудования, или бытовых электрических приборов; - участие в проверке работоспособности оборудования;", "Создание и развитие проектов");
                break;
            }

            case "Инженер-электрик": {
                lite(chatId,"Требования: - консультирование в сфере инженерно-технического проектирования электро-энергетических комплексов, тепло-, гидро- и атомных элетростанций.- проведение расчетов по энергосбережению и повышению энергетической эффективности;- проведение испытаний, исследований и сбор анализов целостных механических и электрических систем, энергетическое обследование;- участие в прокладке и ремонте электросетей в гражданских зданиях, промышленных объектах;- ведение надзор за правильной эксплуатации теплотехнического оборудования, тепло-энергоустановок и сетей теплоснабжения;- обеспечение работы и ремонт электрического оборудования;- консультирование и разработка электростанций и систем для генерации, передачи и распределения электрической энергии; - участие в проверке работоспособности оборудования;", "Создание и развитие проектов");
                break;
            }

            case "Инженер-энергетик": {
                lite(chatId,"Требования: - консультирование и повышению энергетической эффективности и уменьшение элетропотребления разрабатываемого электронного оборудования;- разработка проектов промышленных процессов и производств, относящихся к электротехнике, электронной технике;- проведение испытаний работоспособности электронных устройств;- проведение и анализ испытаний элетроприборов всходящих в устройство сложной техники: авиационной, ракетной и космической;- ведение разработки электронных приборов или компонентов, схем и систем;- определение способов производства и установки, электронных изделий и систем;- разработка и изучение материалов и стандартов качества;- руководство работой по производству или установке электронных изделий и систем;", "Создание и развитие проектов");
                break;
            }

            case "Инженер электроник": {
                lite(chatId,"Требования: - консультирование в области проектирования, связанного со проектированием телекоммуникационной инфраструктуры;- проектирование телекоммуникационного оборудования;- разработка информационных и телекоммуникационных систем, средств защиты и стабильности сигнала;- проведение испытаний работоспособности телекоммуникационных приборов или компонентов, систем, оборудования и распределительных центров;- организация процессов производства и сборки телекоммуникационных систем;- руководство процессом монтажа и установки телекоммуникационных систем;- планирование и проектирование коммуникационных сетей на основе проводных, оптоволоконных и беспроводных каналов связи;- осуществление непосредственного руководства или надзора за строительными работами по возведению сооружений связи (линейно-кабельные и антенно-мачтовые сооружения) и прокладке линий связи;", "Создание и развитие проектов");
                break;
            }

            case "Инженер-разработчик по телекоммуникациям": {
                lite(chatId,"Требования: - осуществление ремонта и наладки бытовой техники различного уровня сложности;- проведение испытаний техники на работоспособность;- проведение анализов свойств материалов из которых изготавливается техника и её компоненты;- осуществление технического контроля над переданным в ведение оборудованием, наладка и замена поврежденных или изношенных элементов;- проведение технических работ в области инженерного дела и технического проектирования;- осуществление настройки, эксплуатации и обслуживание лабораторных приборов и оборудования, мониторинг данных экспериментов, проведение наблюдений, а также расчетов и записей результатов;- понимание и использование технических чертежей для сборки техники или её модернизации;- проведение испытаний отдельных узлов и систем разрабатываемой техники;", "Создание и развитие проектов");
                break;
            }

            case "Техник": {
                lite(chatId,"Требования: - осуществление ремонта и наладки электрического оборудования, систем освещения и электропитания;- проведение испытаний электросетей на работоспособность;- проведение анализов целостности сети электропитания;- осуществление технического контроля над переданным в ведение оборудованием, наладка и замена поврежденных или изношенных элементов;- знание особенностей прокладки электросетей на подведомственном участке;- осуществление контроля за стабильностью работы электросетей отдельных строений и комплексов;- понимание и использование технических чертежей для ведения ремонтных работ и установки нового оборудования;- проведение испытаний отдельных узлов и систем разрабатываемой техники;", "Создание и развитие проектов");
                break;
            }

            case "Электрик.": {
                lite(chatId,"Требования: - планирование и проектирование архитектуры программного обеспечения;- планирование и проектирование шаблонов компьютерных систем с целью их наиболее оптимального взаимодействия в рамках одной информационной сети;- сбор и анализ требований которые- ведение переговоров с заказчиками, интервьюирование их сотрудников для понимания процессов существующих внутри;- изучение предметной области для внедрения и/или разработки прикладных информационных систем;- настройка программного обеспечения и сети под требования заказчика;- участие в подготовке тестирования для выявления отклонений от сформулированных заказчиков требований- организация процесса обучения заказчика, его представителей или сотрудников функционалу системы;", "Создание и развитие проектов");
                break;
            }

            case "Архитектор программного обеспечения": {
                lite(chatId,"Требования: - разработка системы программного обеспечения на основании технического задания и требований;- общение с заказчиками для уточнения требований и периодической демонстрации проделанной работы;- написание компьютерной программы, необходимой для создания и реализации поставленной задачи;- планирование способов соединения, получившегося ПО и существующих систем, с которыми оно должно взаимодействовать;- внесение изменений и настройку приложения таким образом, чтобы оно функционировало в рамках информационной системы;- проведение исследований, анализ и оценка требований к программным приложениям и операционным системам;- проведение консультаций с инженерно-техническим персоналом заказчика;- предоставление сервисных услуг по настройке и обновлению приложений;", "Создание и развитие проектов");
                break;
            }

            case "Разработчик программного обеспечения": {
                lite(chatId,"Требования: - осуществление деятельности по настройке и отладке взаимодействия внутренней и внешней сетей организации;- проектирование и разработку инфраструктуру организации;- оценка и разработка рекомендаций по улучшению работы сети и интегрированного аппаратного, программного обеспечения, коммуникационных и операционных систем; − применение специальных навыков для поддержки и при поиске и устранении неисправностей при работе сети и в аварийных ситуациях;- составление специальных отчетов на основании данных, предоставленных заказчиком;- проведение исследований, анализ, оценка и мониторинг сетевой инфраструктуры для обеспечения оптимальной конфигурации и производительности сетей; - установка, конфигурирование, испытание, поддержка и администрирование новых и обновленных сетей, программных приложений баз данных, серверов и рабочих станций;- Выявление и доработка недостатков передачи сигналов внутри компьютерной сети организации;", "Создание и развитие проектов");
                break;
            }

            case "Сетевой инженер.": {
                lite(chatId,"Требования: - участие в строительстве новых сооружений, реконструкции и ремонте сооружений гражданского назначения;- строительство с использованием инженерно-технических знаний таких сооружений, как автомобильные дороги, в том числе улично-дорожную сеть, искусственные сооружения на них, трамвайные пути, объекты дорожного сервиса, железные дороги, взлетно-посадочные полосы аэропортов и космодромов, водные сооружения, ирригационные системы, системы водоснабжения и сети водоотведения, промышленные предприятия, трубопроводы, линии связи и электропередач, спортивные сооружения и т.д.;- осуществление сборки и монтажа сборных сооружений на строительном участке;- самостоятельное выполнение или оказание помощи при проведении полевых и лабораторных испытаний грунтов и строительных материалов;- оказание технической помощи, связанной со строительством зданий и других сооружений, а также с проведением испытаний или подготовкой докладов о результатах обследований;- соблюдение стандартов, нормативных положений при ведении строительных работ;- знание и соблюдение современного законодательства и технических спецификаций материалов при их подборе и использовании;- оказание помощи в проведении точной оценки количества и стоимости материальных и трудовых затрат для выполнения проекта;", "Создание и развитие проектов");
                break;
            }

            case "Техник-строитель": {
                lite(chatId,"Требования: - участие в строительстве новых сооружений, реконструкции и ремонте сооружений гражданского назначения;- проектирование и доработка чертежей и планов таких сооружений, как автомобильные дороги, в том числе улично-дорожную сеть, искусственные сооружения на них, трамвайные пути, объекты дорожного сервиса, железные дороги, взлетно-посадочные полосы аэропортов и космодромов, водные сооружения, ирригационные системы, системы водоснабжения и сети водоотведения, промышленные предприятия, трубопроводы, линии связи и электропередач, спортивные сооружения и т.д.;- проектирование и контроль за изготовлением сборных сооружений на строительном участке;- самостоятельное выполнение или оказание помощи при проведении полевых и лабораторных испытаний грунтов и строительных материалов;- оказание консультаций по чтению чертежей, типах используемых материалов зданий и других сооружений, а также с проведением испытаний или подготовкой докладов о результатах обследований;- соблюдение стандартов, нормативныхположений при проектировке зданий и строений;- знание и соблюдение современного законодательства и технических спецификаций материалов при их подборе и использовании;- оказание помощи в проведении точной оценки количества и стоимости материальных и трудовых затрат для выполнения проекта;", "Создание и развитие проектов");
                break;
            }

            case "Техник-проектировщик зданий и сооружений": {
                lite(chatId,"Требования: - проведение испытаний и анализов состава и чистоты материалов и веществ, анализ химических и биологических свойств; - наблюдение за деятельностью предприятий и организаций в сфере утилизации мусора и отходов производства;- определение уровней загрязнения окружающей среды по специально разработанным программам;- подготовка и согласование проектных материалов, обосновывающих нормативы допустимых выбросов и сбросов загрязняющих веществ в окружающую среду;- профессиональная помощь специалистам в области защиты окружающей среды в области исследований сфер строительства и утилизации отходов;- выявление нарушений в работе промышленных и коммунальных предприятий и составление рекомендаций о способах исправления ситуации;- оказание технической помощи инженерам при проектировании систем, процессов и оборудования для контроля, управления или восстановления качества воды, воздуха или почвы; − осуществление контроля соответствия технического состояния оборудования требованиям охраны окружающей среды и рационального природопользования;", "Создание и развитие проектов");
                break;
            }

            case "Техник по охране окружающей среды": {
                lite(chatId,"Требования: - осуществление сборки оборудования по управлению производственным процессом;- установка промышленного оборудования, например: коммуникационного оборудования, универсальных компьютеров, облучающей аппаратуры и диагностического оборудования, применяемых в медицинских целях, и т.д.;- подготовка к техническим испытаниям моделей или макетов транспортных и других средств (например, самолетов, ракет, судов, дамб и т.д.);- проведение технических испытаний изделия для его дальнейшей сертификации;- изучение эффективности и целесообразности работы как отдельных узлов, так и целых механизмов, составление отчетов о практичности и надежности изделий;- проектирование и создание макетов машин и механических систем, оборудования и комплектующих материалов в соответствии с установленными техническими параметрами;- осуществление монтажа и установки новых механических узлов, комплектующих изделий, станков и средств контроля;- проведение сборки и установки механических узлов для оказания помощи инженерам-механикам.", "Создание и развитие проектов");
                break;
            }

            case "Техник-механик (общий профиль)": {
                lite(chatId,"Требования: - оказание технической помощи инженерам нефтяникам в процессах изучения и разработки новых промышленных установок и оборудования;- изучение существующего промышленного оборудования по очистке нефти и его аналогов;- обеспечение технического контроля за использованием, обслуживанием и ремонтом химического оборудования и установок по очистке нефти;- монтаж и установка оборудование по очитке и перегонке нефти;- проведение химических и физических лабораторных испытаний для оказания помощи ученым и инженерам в процессе анализа качественных и количественных параметров твердых, жидких и газообразных материалов;- знание и соблюдение технических требований и регламентов к процессам очистки нефти;- умение выстраивать процесс получение конкретных нефтяных фракций, управление процессом очистки;- очистка оборудования и заказ новых деталей и узлов систем очистки нефти, составление отчетов об уровне износа оборудования;", "Создание и развитие проектов");
                break;
            }

            case "Техник по очистке нефти": {
                lite(chatId,"Требования: - проведение разведочного бурения, пробное бурение и отбор образцов породы для геологических или других подобных целей;- предоставление услуг по монтажу, ремонту и демонтажу буровых вышек;- осуществление бурения нефтяных скважин, ведение добычи металлических и неметаллических полезных ископаемых;- обустройство газово-нефтяных скважин, шахт и горнодобывающих комплексов открытого типа путем установки систем вентиляции и откачки, строительства нефтесборных сетей, водоводов, объектов энергетического хозяйства, площадочных объектов;- выполнение работ по проверке, уточнению размеров и особенностей мест при планировании и проектировании рудников, шахт, тоннелей и подземных пунктов оказания первой помощи;- оказание технической помощи специалистам в разработке оборудования для извлечения и переработки полезных ископаемых и металлов; - изучение существующего промышленного оборудования по добыче полезных ископаемых и его аналогов;- осуществление оценки всех затрат, которые необходимо совершить для успешной реализации идущих проектов;", "Создание и развитие проектов");
                break;
            }

            case "Механик по буровым, горным работам": {
                lite(chatId,"Требования: - осуществление направленного бурения и повторного бурения, ударного бурение;- проведение работ по откачке содержимого скважин, заглушка и консервация скважин и т.д.;- проведение работ по пробному бурению на месте предполагаемой добычи нефти или газа;- проведение работ по дренажу и откачке воды на потенциальном месте добычи нефти или газа;- сжижение и обогащение природного газа на месте добычи для последующей транспортировки;- оказание технической помощи инженерам в области бурения скважин, добычи, хранения и транспортировки нефти и газа;- обслуживание и контроль за эксплуатацией оборудования в условиях износа оборудования; оказание помощи исследователям в проектировании технических средств как для бурения скважин, так и для перевозки нефти;", "Создание и развитие проектов");
                break;
            }

            case "Техник-энергетик; Техник-электрик": {
                lite(chatId,"Требования: - осуществление строительства линий электропередач, электроподстанций и электростанций;- участие в строительстве сооружений гражданского строительства, таких как междугородные и городские линии распределения электроэнергии и линии связи, электростанции, сооружения связи, включая линейно-кабельные и антенно-мачтовые сооружения;- обеспечение работы распределяющей системы электроэнергии (электростанций и подстанций);- проведение исследований целостности электрических систем, энергетическое обследование качества работы оборудования;-помощь инженерам в подготовке чертежей и схем электроустановок в соответствии с заданными техническими параметрами;- обеспечение технического контроля за использованием, обслуживанием и ремонтом электрических систем и оборудования; − планирование методов установки, осуществление первоначального запуска нового электрического оборудования и систем; − тестирование электротехнического оборудования и установок в соответствии с правилами и требованиями безопасности их работы;", "Создание и развитие проектов");
                break;
            }

            case "Техник-электроник": {
                lite(chatId,"Требования: - ведение разработки микросхем, и изучение высоких специализированных технологий и микроэлектроники;- осуществление сборки оборудования по управлению производственным процессом;- организация установки сложного электронного оборудования на производстве, в медицинских центрах и исследовательских лабораториях;- участие в отключении и демонтаже сложного электронного оборудования;- оказание технической помощи инженерам при тестировании работы различных электронных устройств;- контроль или осуществление первоначального запуска нового электронного оборудования и систем;- проведение испытаний электронных систем, сбор и анализ полученных данных, сборка электронных схем для оказания помощи инженерам-электроникам;- ремонт микросхем и диагностика неполадок, проверка целостности системы всего устройства;", "Создание и развитие проектов");
                break;
            }

            case "Техник авиационной безопасности": {
                lite(chatId,"Требования: - проведение подготовки авиационной, ракетной и космической техники к летным испытаниям;- выполнение испытаний, исследований и анализа целостных механических и электрических систем, энергетическое обследование летательных аппаратов;- участие в производство радиолокационной, радионавигационной аппаратуры и радиоаппаратуры дистанционного управления;- проверка на работоспособность и наладка радиолокационной и навигационной аппаратуры;- тестирование, проверка и анализ работы оборудования, помощь в развитии аэронавигационных систем путем предоставление отчетов о качестве работы эксплуатируемых систем;- разработка конкретных аэронавигационных схем и систем слежения за полетами;- усовершенствование действующих наземных аэронавигационных систем и оборудования, и их адаптация к новым процедурам управления воздушным движением с целью расширения возможностей, повышения надежности и целостности систем управления воздушным движением, обеспечения безопасности воздушного пространства; − контроль, мониторинг и сертификация средств навигации, связи и оборудования для наблюдения за воздушным движением, выверка наземных аэронавигационных систем для обеспечения максимальной точности и безопасности полета, взлета и посадки;", "Создание и развитие проектов");
                break;
            }

            case "Техник по монтажу телекоммуникационного оборудования": {
                lite(chatId,"Требования: - инженерно-техническое консультирование при монтаже телекоммуникационного оборудования;- монтаж различных телекоммуникаций: кабельного телевидения, включая оптоволоконные линии связи, антенн всех типов, включая спутниковые антенны;- ремонт и обслуживание коммуникационного оборудования, такого как: радиотелефоны, сотовые телефоны, модемы высокочастотного оборудования и т.д.;- осуществление деятельности по разработке информационных и телекоммуникационных систем, и способов их защиты;- оказание технической помощи, связанной с исследованиями и развитием телекоммуникационного оборудования или тестированием прототипов; − изучение технических материалов, таких как чертежи и эскизы, с целью определения методов работы, которые будут приняты; − подготовка отчетов о стоимости работ по монтажу и установке оборудования, сопутствующей техники и материалов; − обеспечение технического надзора за производством, использованием, содержанием и ремонтом телекоммуникационных систем;", "Создание и развитие проектов");
                break;
            }

            case "Электрик-строитель-монтажник аппаратуры и кабелей низкого напряжения; Электромонтажник по освещению и осветительным сетям": {
                lite(chatId,"Требования: - осуществление установки электротехнических систем во всех видах жилых зданий и сооружений;- проведение монтажа электропроводки и электроарматуры, уличного освещения и иного электрооборудования на автомобильных дорогах;- выполнение работ по подводке электросетей для подключения электроприборов, кодовых замков, домофонов и прочего оборудования, включая плинтусное отопление;- осуществление обслуживания междугородних линий электропередачи и связи;- осуществление монтажа, технического обслуживания и ремонта систем электропроводки и сопутствующего оборудования в различных зданиях – школах, больницах, торговых учреждениях, жилых домах и других сооружениях; − изучение светокопий, схем электропроводки и спецификаций для определения последовательности и методов работы; − планирование компоновки и установка электрической проводки, оборудования и арматуры на основе рабочих заданий и соответствующих стандартов; − проверка электрических систем, оборудования и компонентов для выявления рисков, дефектов и необходимости в регулировке или ремонте;", "Создание и развитие проектов");
                break;
            }

            case "Электромеханик; Электромонтер": {
                lite(chatId,"Требования: - выполнение работ по проводке электросетей для подключения различного оборудования;- осуществление энергообеспечения наземного электротранспорта и электротехнического сигнального оборудования, освещения взлетно-посадочных полос аэропортов и космодромов, электрических коллекторов солнечной энергии;- проведение ремонта и монтажа машин и оборудования;- проведение ремонта электронного и оптического оборудования;- сборка, настройка и ремонт различных видов электрических машин и моторов, генераторов, распределительных устройств, контрольно-измерительной аппаратуры, приборов или электрических компонентов подъемников и аналогичного оборудования; − сборка, настройка и ремонт электрических компонентов предметов домашнего обихода, промышленных машин и других аппаратов; − установка и ввод в эксплуатацию, техническое обслуживание и модификация электрического оборудования, проводки и контрольных систем; − проектирование, установка, техническое обслуживание и ремонт электрических и гидравлическихчпассажирских и грузовых лифтов, эскалаторов, движущихся дорожек и другого подъемного оборудования;", "Создание и развитие проектов");
                break;
            }

            case "Преподаватель, профессор информационных систем, ВУЗ; Преподаватель, доцент, профессор архитектуры": {
                lite(chatId,"Требования: - разработка учебных программ; - планирование содержания курса и методов преподавания; − чтение лекций и проведение практических занятий для повышения знаний и навыков студентов;- проведение устных, письменных и практических работ для выяснения уровня знаний и умений студентов;− проведение учебных курсов без отрыва от производства для преподавания и демонстрации принципов, методик, процедур или методов отдельных предметов;- осуществление подготовки высококвалифицированных кадров;- ведение научной деятельности по изучению отдельных предметов и областей с проведением лабораторных исследований и экспериментов;- осуществление подготовки квалифицированных рабочих и специалистов среднего звена;", "Создание и развитие проектов");
                break;
            }

            case "IT и информационная безопасность": {
                var response = new SendMessage(chatId, "IT и информационная безопасность");

                response.replyMarkup(new ReplyKeyboardMarkup(
                        new String[] {"Разработка It-продуктов"},
                        new String[] {"Информационная безопасность"},
                        new String[] {"Инновации"}
                ).resizeKeyboard(true));

                this.execute(response);
                break;
            }

            case "Разработка It-продуктов": {
                var response = new SendMessage(chatId, "Разработка It-продуктов");

                response.replyMarkup(new ReplyKeyboardMarkup(
                        new String[] {"Архитектор программного обеспечения; Консультант по системам; IT-дизайнер"},
                        new String[] {"Инженер-программист; Программист-аналитик; Разработчик мобильных приложений; Разработчик мультимедиа"},
                        new String[] {"Преподаватель (переподготовка и повышение квалификации)"},
                        new String[] {"IT и информационная безопасность"}
                ).resizeKeyboard(true));

                this.execute(response);
                break;
            }

            case "Архитектор программного обеспечения; Консультант по системам; IT-дизайнер": {
                lite(chatId,"Требования: - создание программного обеспечения; - создание веб-страниц; - создание баз данных; - модификация программного обеспечения для нужд клиентов; - консультирование пользователей информационных систем; - испытания программного обеспечения; - разработка функциональных требований для программ и веб-сайтов; - координация компьютерных систем внутри организации.", "Разработка It-продуктов");
                break;
            }

            case "Инженер-программист; Программист-аналитик; Разработчик мобильных приложений; Разработчик мультимедиа": {
                lite(chatId,"Требования: - программирование приложений и баз данных; - настройка программного обеспечения; - обработка данных; - разработка методов защиты данных; - разработка сайтов; - контроль и обеспечение безопасности передачи информации в сети Интернет; - разработка и проведение испытаний криптографического программного обеспечения - консультирование клиентов.", "Разработка It-продуктов");
                break;
            }

            case "Преподаватель (переподготовка и повышение квалификации)": {
                lite(chatId,"Требования: - проведение теоретических и практических занятий для работников; - консультации по вопросам информационной безопасности; - обучение навыкам работы с оборудованием и программным обеспечением для защиты информации; - разработка программы курса; повышения квалификации; - контроль за оснащением рабочих мест; - контроль профессионального уровня и проведение аттестации работников; - выдача заключений о профессиональной пригодности и удостоверений о повышении квалификации.", "Разработка It-продуктов");
                break;
            }

            case "Информационная безопасность": {
                var response = new SendMessage(chatId, "Информационная безопасность");

                response.replyMarkup(new ReplyKeyboardMarkup(
                        new String[] {"Сетевой администратор; Системный администратор"},
                        new String[] {"Аудитор по информационной безопасности; Инженер по защите информации; Специалист по информационной безопасности"},
                        new String[] {"Преподаватель, доцент, профессор университетов и других ВУЗов в области военного дела и безопасности"},
                        new String[] {"Полицейский; Специалист по расследованию киберпреступлений"},
                        new String[] {"IT и информационная безопасность"}
                ).resizeKeyboard(true));

                this.execute(response);
                break;
            }

            case "Сетевой администратор; Системный администратор": {
                lite(chatId,"Требования: - изменение программного обеспечения по заказу клиента; - управление компьютерными системами; - установка программного обеспечения - обработка данных; - администрирование компьютерных систем; - диагностика проблем в работе компьютерной техники и программного обеспечения; - защита и восстановление данных; - координация доступа к компьютерной сети", "Информационная безопасность");
                break;
            }

            case "Аудитор по информационной безопасности; Инженер по защите информации; Специалист по информационной безопасности": {
                lite(chatId,"Требования: - изменение программного обеспечения по заказу клиента; - управление компьютерными системами; - разработка средств и планов защиты информации; - разработка защищенных информационных и телекоммуникационных систем; - обучение и привлечение внимания пользователей к проблемам безопасности; - мониторинг текущих отчетов о компьютерных вирусах; - регулирование доступа компьютерам с целью защиты информации; - шифрование передаваемых данных.", "Информационная безопасность");
                break;
            }

            case "Преподаватель, доцент, профессор университетов и других ВУЗов в области военного дела и безопасности": {
                lite(chatId,"Требования: - обучение студентов навыкам критического и системного мышления; - проведение научных исследований в области защиты информации; - консультации по вопросам информационной безопасности; - исследования в области информационной безопасности вооруженных сил; - разработка и изменение учебных программ; - проведение учебных занятий; - проверка и оценка контрольных работ и экзаменов; - руководство научной работой студентов.", "Информационная безопасность");
                break;
            }

            case "Полицейский; Специалист по расследованию киберпреступлений": {
                lite(chatId,"Требования: - мониторинг интернет-пространства для выявления вредоносной информации; - защита частных лиц и их имущества, в том числе носителей и средств передачи информации; - расследование информационных преступлений; - информирование частных лиц об информационных опасностях и рисках; - защита персональных данных и другой конфиденциальной информации о гражданах; - совмещение знания правовых норм и владения методами и средствами защиты информации; - поиск, преследование и арест подозреваемых и преступников, - оказание неотложной помощи жертвам преступлений.", "Информационная безопасность");
                break;
            }

            case "Творчество": {
                var response = new SendMessage(chatId, "Творчество");

                response.replyMarkup(new ReplyKeyboardMarkup(
                        new String[] {"Создание нового"},
                        new String[] {"Инновации"}
                ).resizeKeyboard(true));

                this.execute(response);
                break;
            }

            case "Создание нового": {
                var response = new SendMessage(chatId, "Создание нового");

                response.replyMarkup(new ReplyKeyboardMarkup(
                        new String[] {"Инструктор по росписи; Инструктор по танцам"},
                        new String[] {"Дизайнер игрушек; Дизайнер промышленных изделий; Дизайнер ювелирных украшений;  Модельер одежды"},
                        new String[] {"Главный редактор (издательства, редакции газет и журналов); Директор съемочной группы; Заведующий редакцией"},
                        new String[] {"Карикатурист; Скульптор; Художник; Художник-живописец; Художник-иллюстратор;  Художник-реставратор"},
                        new String[] {"Аккомпаниатор; Артист ансамбля песни и танца;  Артист оркестра; Артист хора; Артист-вокалист (оперный и камерный)"},
                        new String[] {"Артист балета; Балетмейстер; Хореограф"},
                        new String[] {"Звукорежиссер; Кинорежиссер; Ассистент художника по комбинированным съемкам; Оператор постановщик"},
                        new String[] {"Актер;  Артист драмы;  Артист мимического ансамбля"},
                        new String[] {"Артист жанра Сатира; Артист-воздушный гимнаст; Артист спортивно- акробатического жанра; Артист цирка"},
                        new String[] {"Автор сценария;  Драматург; Кинокритик; Очеркист; Писатель; Писатель-романист;  Поэт; Редактор книг"},
                        new String[] {"Преподаватель; доцент;  профессор; Преподаватель, доцент, профессор театрального искусства"},
                        new String[] {"Творчество"}
                ).resizeKeyboard(true));

                this.execute(response);
                break;
            }

            case "Инструктор по росписи; Инструктор по танцам": {
                lite(chatId,"Требования: Уметь планировать и руководить повседневной деятельностью. Грамотно составить планы и программы занятий, обеспечить дальнейшее их управление. Способность провести обучение, демонстрацию упражнений. Способность провести рациональное оценивание достигнутых результатов обучаемых.", "Создание нового");
                break;
            }

            case "Дизайнер игрушек; Дизайнер промышленных изделий; Дизайнер ювелирных украшений;  Модельер одежды": {
                lite(chatId,"Требования: Способность четко определить цели и ограничения, связанных с заданием на проектирование, посредством консультации с клиентами и заинтересованными лицами. Согласовывать соображения эстетического порядка с техническими, функциональными, экологическими и технологическими требованиями. Умение осуществлять надзор за изготовлением образцов, программ и инструментов, а также за самим процессом изготовления. Способность сделать выбор, описать и дать рекомендации касательно функциональных и эстетических материалов, методов производства и отделки производства. Способность четко определить цели и ограничения, связанные с заданием на проектирование, посредством консультаций с клиентами и заинтересованными лицами. Согласовывать соображения эстетического порядка с техническими, функциональными, экологическими и технологическими требованиями. Умение подготовить эскизы, диаграммы, иллюстрации и макеты для передачи концепций дизайна. Создавать двухмерные и трехмерные изображения, отображающие объекты в движении или иллюстрирующие какой-либо процесс, с использованием программ компьютерной анимации или моделирования.", "Создание нового");
                break;
            }

            case "Главный редактор (издательства, редакции газет и журналов); Директор съемочной группы; Заведующий редакцией": {
                lite(chatId,"Требования: Способность определить, осуществить и провести мониторинг реализации планов по производству и созданию кино-, видеофильмов, и телевизионных программ, фонограмм и музыкальных записей, а также управление издательской деятельностью. Организовывать выполнение работ по производству программ в установленные сроки и в пределах утвержденного бюджета. Умение заключать договоры с необходимыми поставщиками и контролировать соблюдение требований к качеству, срокам и стоимости. Планировать и руководить повседневной деятельностью.", "Создание нового");
                break;
            }

            case "Карикатурист; Скульптор; Художник; Художник-живописец; Художник-иллюстратор;  Художник-реставратор": {
                lite(chatId,"Требования: Умение разрабатывать идеи, конструктивные решения и стили для живописи, рисунков, скульптур. Способность создавать изображения или абстрактные рисунки и картины используя карандаши, чернила, мел, масляные краски, акварель и т.п. Создавать рисунки и их гравировки или вытравливания на металле, дереве или других материалах. Навык реставрации поврежденных, загрязненных и выцветших картин и других предметов искусства.", "Создание нового");
                break;
            }

            case "Аккомпаниатор; Артист ансамбля песни и танца;  Артист оркестра; Артист хора; Артист-вокалист (оперный и камерный)": {
                lite(chatId,"Требования: Умение создавать мелодические, ритмические и гармонические структуры для выражения идей и чувств в музыкальной форме. Преобразовывать идеи и концепции в стандартные музыкальные знаки и символы для воспроизведения и исполнения. Способность адаптировать или создавать аранжировку музыки для определенных инструментальных или вокальных групп, инструментов или мероприятий. Навык пения в качестве солиста или члена вокальной группы или другого коллектива.", "Создание нового");
                break;
            }

            case "Артист балета; Балетмейстер; Хореограф": {
                lite(chatId,"Требования: Навык замысла и создания танцев, в которых сюжет, тема, идея или настроение передаются определенными танцевальными па, движениями и жестами. Умение исполнить танец в качестве солиста, с партнером или в качестве участника танцевального коллектива перед живой аудиторией или для фильма, телевидения или других визуальных носителей информации. Обязательное посещение танцевальных занятий, тренировок для поддержания необходимого уровня способностей и физической формы.", "Создание нового");
                break;
            }

            case "Звукорежиссер; Кинорежиссер; Ассистент художника по комбинированным съемкам; Оператор постановщик": {
                lite(chatId,"Требования: Умение качественно координировать производство музыкальных произведений с музыкальными руководителями. Умение подобрать писателей, подробно изучить сценарии для разработки художественной интерпретации, а также способность четко объяснить актерам методы игры.", "Создание нового");
                break;
            }

            case "Актер;  Артист драмы;  Артист мимического ансамбля": {
                lite(chatId,"Требования: Умение запоминать в краткий срок стихи, реплики, а также способность исполнить роль в драматических постановках на сцене, в рекламе, на телевидении и т.п. Навык вхождения в роль, созданную драматургом или автором, представление роли зрителям. Артистичное рассказывание историй или выразительное чтение вслух литературных произведений для обучения или развлечения слушателей. Обязательное посещение просмотров и прослушиваний на роли.", "Создание нового");
                break;
            }

            case "Артист жанра Сатира; Артист-воздушный гимнаст; Артист спортивно- акробатического жанра; Артист цирка": {
                lite(chatId,"Требования: Навык лицедейства и пересказа смешных историй. Способность развлечь зрителей посредством иллюзий, показа фокусов, трюков и гипноза. Умение представить сложные и захватывающие акробатические, гимнастические номера и жонглирование. Навык дрессировки животных и выступления с их участием.", "Создание нового");
                break;
            }

            case "Автор сценария;  Драматург; Кинокритик; Очеркист; Писатель; Писатель-романист;  Поэт; Редактор книг": {
                lite(chatId,"Требования: Способность к написанию и редактированию романов, пьес, сценариев, стихов и других материалов для публикации или презентации. Умение провести исследования для установления фактов и получения другой необходимой информации. Навык написания сценариев и текстов, постановок, а также подготовка программ для постановки в театре, кино, на радио и телевидении. Умение анализировать материал, включая спецификации, примечания и чертежи, дальнейшая подготовка руководств, инструкций по использованию и других документов для четкого и краткого объяснения процесса установки, эксплуатации и технического обслуживания программного обеспечения и оборудования.", "Создание нового");
                break;
            }

            case "Преподаватель; доцент;  профессор; Преподаватель, доцент, профессор театрального искусства": {
                lite(chatId,"Требования: Навык разработки и изменения учебных программ, подготовки учебных курсов по преподаваемым предметам в соответствии с требованиями. Знание и чтение лекций, проведение учебных занятий, семинаров и лабораторных работ. Способность побудить студентов к дискуссиям и независимым размышлениям. Умение наблюдать за экспериментальной и практической работой студентов.", "Создание нового");
                break;
            }

            case "Организация": {
                var response = new SendMessage(chatId, "Организация");

                response.replyMarkup(new ReplyKeyboardMarkup(
                        new String[] {"Организация мероприятий"},
                        new String[] {"Организация деятельности"},
                        new String[] {"Инновации"}
                ).resizeKeyboard(true));

                this.execute(response);
                break;
            }

            case "Организация мероприятий": {
                var response = new SendMessage(chatId, "Организация мероприятий");

                response.replyMarkup(new ReplyKeyboardMarkup(
                        new String[] {"Организация мероприятий"},
                        new String[] {"Организация деятельности"},

                        new String[] {"Организация"}
                ).resizeKeyboard(true));

                this.execute(response);
                break;
            }

            case "Организация деятельности": {
                var response = new SendMessage(chatId, "Организация деятельности");

                response.replyMarkup(new ReplyKeyboardMarkup(
                        new String[] {"Организация мероприятий"},
                        new String[] {"Организация деятельности"},

                        new String[] {"Организация"}
                ).resizeKeyboard(true));

                this.execute(response);
                break;
            }

            case "Спорт": {
                var response = new SendMessage(chatId, "Спорт");

                response.replyMarkup(new ReplyKeyboardMarkup(
                        new String[] {"Занятия спортом"},
                        new String[] {"Тренерская деятельность"},

                        new String[] {"Инновации"}
                ).resizeKeyboard(true));

                this.execute(response);
                break;
            }

            case "Занятия спортом": {
                var response = new SendMessage(chatId, "Занятия спортом");

                response.replyMarkup(new ReplyKeyboardMarkup(
                        new String[] {"Занятия спортом"},
                        new String[] {"Тренерская деятельность"},

                        new String[] {"Спорт"}
                ).resizeKeyboard(true));

                this.execute(response);
                break;
            }

            case "Тренерская деятельность": {
                var response = new SendMessage(chatId, "Тренерская деятельность");

                response.replyMarkup(new ReplyKeyboardMarkup(
                        new String[] {"Занятия спортом"},
                        new String[] {"Тренерская деятельность"},

                        new String[] {"Спорт"}
                ).resizeKeyboard(true));

                this.execute(response);
                break;
            }

            default: {
                    SendMessage response = new SendMessage(chatId, "Команда не найдена");
                    this.execute(response);
                    break;
            }
        }
    }

    public void lite(Long chatId, String text, String exit) {
        var response = new SendMessage(chatId, text);

        var response1 = new SendMessage(chatId, "Благодарю Вас за Ваш выбор. А теперь – три самых важных" +
                " вопроса... по ссылке");

        var response2 = new SendMessage(chatId, "Для возвращения назад кликните ниже!");

        response1.replyMarkup(new InlineKeyboardMarkup(
                new InlineKeyboardButton("Ссылка").url("https://docs.google.com/forms/d/e/1FAIpQLSfbj8-v" +
                        "FREt7d9V0iNgY4LSoe2YWnHFndF7GbehyNzI2mvkAw/viewform")
        ));

        response.replyMarkup(new ReplyKeyboardMarkup(
                new KeyboardButton(exit)
        ).resizeKeyboard(true));

        this.execute(response);
        this.execute(response1);
        this.execute(response2);
    }
}
