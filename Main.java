import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

/* Напишите приложение, которое будет запрашивать у пользователя следующие данные, разделенные пробелом:

Фамилия Имя Отчество дата _ рождения номер _ телефона пол

Форматы данных:

фамилия, имя, отчество - строки
дата _ рождения - строка формата dd.mm.yyyy
номер _ телефона - целое беззнаковое число без форматирования
пол - символ латиницей f или m.

Приложение должно проверить введенные данные по количеству. Если количество не совпадает, вернуть код ошибки, обработать его и показать пользователю сообщение, что он ввел меньше и больше данных, чем требуется.

Приложение должно распарсить полученную строку и выделить из них требуемые значения. Если форматы данных не совпадают, нужно бросить исключение, соответствующее типу проблемы. Можно использовать встроенные типы java и создать свои. Исключение должно быть корректно обработано, пользователю выведено сообщение с информацией, что именно неверно.

Если всё введено и обработано верно, должен создаться файл с названием, равным фамилии, в него в одну строку должны записаться полученные данные, вида
<Фамилия> <Имя> <Отчество> <дата _ рождения> <номер _ телефона> <пол>

Однофамильцы должны записаться в один и тот же файл, в отдельные строки.
Не забудьте закрыть соединение с файлом.
При возникновении проблемы с чтением-записью в файл, исключение должно быть корректно обработано, пользователь должен увидеть стектрейс ошибки.

Данная промежуточная аттестация оценивается по системе "зачет" / "не зачет"

"Зачет" ставится, если слушатель успешно выполнил задание.

"Незачет" ставится, если слушатель не выполнил задание.

Критерии оценивания: Слушатель написал приложение, которое запрашивает у пользователя следующие данные, разделенные пробелом: Фамилия Имя Отчество дата _ рождения номер _ телефона пол */

class NameException extends Exception {
    public NameException() {
        super("ФИО должно содержать 3 слова.");
    }
}

class DateException extends Exception {
    public DateException() {
        super("Дата должна быть в формате dd.mm.yyyy и состоять из целых чисел.");
    }
}

public class Main {

    public static void checkName(String fio) throws NameException {
        String[] fioList = fio.split(" ");
        // System.out.println(Arrays.toString(fioList));
        if (fioList.length != 3) {
            throw new NameException();
        }
    }

    public static void checkDate(String birthDate) throws DateException {
        String[] birthDateList = birthDate.split("\\.");
        // System.out.println(Arrays.toString(birthDateList));
        if (birthDateList.length != 3) {
            throw new DateException();
        }
        try {
            Integer day = Integer.parseInt(birthDateList[0]);
            if (day > 32 || day < 1) {
                System.out.println("День должен быть в диапазоне от 1 до 31.");
                throw new DateException();
            }
            Integer month = Integer.parseInt(birthDateList[1]);
            if (month > 12 || month < 1) {
                System.out.println("Месяц должен быть в диапазоне от 1 до 12.");
                throw new DateException();
            }
            Integer year = Integer.parseInt(birthDateList[2]);
            if (year > 2024 || year < 1900) {
                System.out.println("Год должен быть в диапазоне от 1900 до 2024.");
                throw new DateException();
            }
        } catch (NumberFormatException e) {
            throw new DateException();
        }
    }

    public static void main(String[] args) throws NameException, DateException {

        Scanner scanner = new Scanner(System.in);
        try {
            System.out.println("Введите ФИО: ");
            String fio = scanner.nextLine();
            checkName(fio);
            String[] fioList = fio.split(" ");

            System.out.println("Введите дату рождения в формате dd.mm.yyyy: ");
            String birthDate = scanner.nextLine();
            checkDate(birthDate);

            System.out.println("Введите телефон: ");
            String phoneStr = scanner.nextLine();
            Integer phone = Integer.parseInt(phoneStr);

            String idGender = "0";
            String gender = "";
            while (!idGender.equals("1") && !idGender.equals("2")) {
                System.out.println("Выберите пол:\n" +
                        "1 - мужской;\n" +
                        "2 - женский.");
                idGender = scanner.next();
            }
            if (idGender.equals("1")) {
                gender = "муж.";
            } else {
                gender = "жен.";
            }

            String newNote = String.format("<%s> <%s> <%s> <%s> <%d> <%s>", fioList[0], fioList[1], fioList[2],
                    birthDate, phone, gender);
            // System.out.println(newNote);
            String fileName = fioList[0] + ".txt";
            System.out.println(fileName);
            File file = new File(fileName);

            try {
                if (file.createNewFile()) {
                    System.out.println("Файл " + fileName + " успешно создан.");

                } else {
                    System.out.println("Файл " + fileName + " уже существует.");
                }
                FileWriter writer = new FileWriter(file, true);
                writer.write(newNote + "\n");
                writer.close();
            } catch (IOException e) {
                System.out.println("Произошла ошибка при создании файла");
            }

        } catch (NameException e) {
            System.out.println("Неверно введено ФИО.");
        } catch (NumberFormatException e) {
            System.out.println("Номер телефона должен содержать только цифры.");
        } catch (DateException e) {
            System.out.println("Дата должна быть в формате dd.mm.yyyy и состоять из целых чисел.");
        }

        scanner.close();
    }

}