package org.example;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.BotCommand;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.*;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.request.SetMyCommands;

import java.util.Objects;
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


                response.replyMarkup(new ReplyKeyboardMarkup("Согласен ", "Расскажи немного о том, что меня ждет."));

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
                        new String[] {"Государственная служба", "Культура", "Вернуться назад"}
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

                /*response.replyMarkup(new ReplyKeyboardMarkup(
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
                ));*/

                this.execute(response);
                break;
            }

            case "Организационная помощь": {

            }





            case "Защита": {

            }

            case "Хранение": {

            }

            case "Коммуникации": {

            }

            case "Инновации": {

            }


            case "Расск1": {
                SendMessage response = new SendMessage(chatId, "Вы введи команду Batten #1");
                response.replyMarkup(new ReplyKeyboardMarkup(
                        new String[] {"Batten #5", "Batten #6"},
                        new String[] {"Batten #7", "Batten #8"}
                ));
                this.execute(response);
                break;
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
}
