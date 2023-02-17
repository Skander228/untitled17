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
                        new String[] {"Организационная поддержка"},
                        new String[] {"Помощь по хозяйству"},
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

            case "Коммуникации": {

            }

            case "Инновации": {

            }

            case "Расск11": {
                var response = new SendMessage(chatId, "eee");
                response.replyMarkup(new InlineKeyboardMarkup(
                        new InlineKeyboardButton("url").url("skander.com"),
                        new InlineKeyboardButton("callback_Data").callbackData("callbackData"),
                        new InlineKeyboardButton("switchInlineQuery").switchInlineQuery("switch_Inline_Query")
                ));
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
