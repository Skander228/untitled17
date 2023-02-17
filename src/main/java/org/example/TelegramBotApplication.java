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
                var response = new SendMessage(chatId, "Вы выбрали Организационная поддержка");

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
                var response = new SendMessage(chatId, "Вы выбрали Поддержка по хозяйству");

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
                var response = new SendMessage(chatId, "Вы выбрали Сады, парки, редкие растения и животные");

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
                var response = new SendMessage(chatId, "Вы выбрали Сады, парки, редкие растения и животные");

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
                var response = new SendMessage(chatId, "Вы выбрали Сады, парки, редкие растения и животные");

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
