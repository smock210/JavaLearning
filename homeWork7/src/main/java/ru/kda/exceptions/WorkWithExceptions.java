package ru.kda.exceptions;

public class WorkWithExceptions
{
    /**
     * В данном методе необходимо вызвать методы throwCheckedException и throwUncheckedException.
     * Все исключения должны быть обработаны
     * Необходимо вывести описание exception и stacktrace в консоль
     * В пойманное исключение необходимо упаковать в кастомное исключение и пробросить выше
     * Перед завершением работы метода обязательно необходимо вывести в консоль сообщение "Finish"
     * поработайте над подавленными исключениями
     */
    public void exceptionProcessing()
    {
        /*
        ...
         */
    }

    /**
     * (* - необязательно) Доп. задание.
     * Выписать в javadoc (здесь) - все варианты оптимизации и устранения недочётов метода
     * @throws IllegalStateException
     * @throws Exception
     * @throws RuntimeException
     */
    public void hardExceptionProcessing() throws IllegalStateException, Exception, RuntimeException
    {
        System.out.println("Start");
        try
        {
            System.out.println("Step 1");
            throw new IllegalArgumentException();
        }
        catch (IllegalArgumentException e)
        {
            System.out.println("Catch IllegalArgumentException");
            throw new RuntimeException("Step 2");
        }
        catch (RuntimeException e)
        {
            System.out.println("Catch RuntimeException");
            throw new RuntimeException("Step 3");
        }
        finally
        {
            System.out.println("Step finally");
            throw new RuntimeException("From finally");
        }
    }

    private void throwCheckedException() throws Exception
    {
        throw new Exception("Checked exception");
    }

    private void throwUncheckedException()
    {
        throw new RuntimeException("Unchecked exception");
    }
}
