package ru.kda.io;

import java.io.*;

public class MyIOExample
{
    /**
     * Создать объект класса {@link java.io.File}, проверить существование и чем является (файл или директория).
     * Если сущность существует, то вывести в консоль информацию:
     *      - абсолютный путь
     *      - родительский путь
     * Если сущность является файлом, то вывести в консоль:
     *      - размер
     *      - время последнего изменения
     * Необходимо использовать класс {@link java.io.File}
     * @param fileName - имя файла
     * @return - true, если файл успешно создан
     */
    public boolean workWithFile(String fileName)
    {
        if (fileName != null)
        {
            File file = new File(fileName);
            if (file.exists()){
                System.out.println("абсолютный путь: " + file.getAbsolutePath());
                System.out.println("родительский путь: " + file.getParent());
            }
            if (file.isFile()){
                System.out.println("размер: " + file.length());
                System.out.println("время последнего изменения: " + file.lastModified());
            }
            return true;
        }
        /*
        ...
         */
        return false;
    }

    /**
     * Метод должен создавать копию файла
     * Необходимо использовать IO классы {@link java.io.FileInputStream} и {@link java.io.FileOutputStream}
     * @param sourceFileName - имя исходного файла
     * @param destinationFileName - имя копии файла
     * @return - true, если файл успешно скопирован
     */
    public boolean copyFile(String sourceFileName, String destinationFileName)
    {

        if (sourceFileName != null && destinationFileName != null)
        {
            File fileSours = new File(sourceFileName);
            File fileDest = new File(destinationFileName);
            if (!fileSours.exists()){
                System.out.println("Файл не найден");
            }
            try{
                FileInputStream fileInputStream = new FileInputStream(fileSours);
                FileOutputStream fileOutputStream = new FileOutputStream(fileDest);

                int byteData;
                while ((byteData = fileInputStream.read()) != -1) {
                    fileOutputStream.write(byteData);
                }

            } catch (FileNotFoundException e) {
                System.err.println("Ошибка файл не найден: " + e.getMessage());
                return false;
            } catch (IOException e) {
                System.err.println("Ошибка при копировании файла: " + e.getMessage());
                return false;
            }

            return true;
        }
        return false;
    }

    /**
     * Метод должен создавать копию файла
     * Необходимо использовать IO классы {@link java.io.BufferedInputStream} и {@link java.io.BufferedOutputStream}
     * @param sourceFileName - имя исходного файла
     * @param destinationFileName - имя копии файла
     * @return - true, если файл успешно скопирован
     */
    public boolean copyBufferedFile(String sourceFileName, String destinationFileName)
    {
        if (sourceFileName != null && destinationFileName != null)
        {
            File fileSours = new File(sourceFileName);
            File fileDest = new File(destinationFileName);
            if (!fileSours.exists()){
                System.out.println("Файл не найден");
            }
            try{
                BufferedInputStream fileInputStream = new BufferedInputStream(new FileInputStream(fileSours));
                BufferedOutputStream fileOutputStream = new BufferedOutputStream(new FileOutputStream(fileDest));

                int byteData;
                while ((byteData = fileInputStream.read()) != -1) {
                    fileOutputStream.write(byteData);
                }

            } catch (FileNotFoundException e) {
                System.err.println("Ошибка файл не найден: " + e.getMessage());
                return false;
            } catch (IOException e) {
                System.err.println("Ошибка при буферном копировании: " + e.getMessage());
                return false;
            }

            return true;
        }
        return false;
    }

    /**
     * Метод должен создавать копию файла
     * Необходимо использовать IO классы {@link java.io.FileReader} и {@link java.io.FileWriter}
     * @param sourceFileName - имя исходного файла
     * @param destinationFileName - имя копии файла
     * @return - true, если файл успешно скопирован
     */
    public boolean copyFileWithReaderAndWriter(String sourceFileName, String destinationFileName)
    {
        if (sourceFileName != null && destinationFileName != null)
        {
            File fileSours = new File(sourceFileName);
            File fileDest = new File(destinationFileName);
            if (!fileSours.exists()){
                System.out.println("Файл не найден");
            }
            try{
                FileReader fileInput = new FileReader(fileSours);
                FileWriter fileOutput = new FileWriter(fileDest);

                int charInput;
                while ((charInput = fileInput.read()) != -1) {
                    fileOutput.write(charInput);
                }

            } catch (FileNotFoundException e) {
                System.err.println("Ошибка файл не найден: " + e.getMessage());
                return false;
            } catch (IOException e) {
                System.err.println("Ошибка при буферном копировании: " + e.getMessage());
                return false;
            }

            return true;
        }
        return false;
    }
}
