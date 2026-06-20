package beans;

import ai_code.AthletesInjuries;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import jakarta.faces.application.ConfigurableNavigationHandler;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.util.Callbacks.SerializableSupplier;

/**
 *
 * @author asdv5
 */
@Named(value = "testBean")
@ViewScoped
public class TestBean implements Serializable
{

    InputStream is;
    private AthletesInjuries ai = new AthletesInjuries();
    private String results;
    private StreamedContent fileStreamContent;

    private int activeIndex = 0;//for steps

    public TestBean()
    {
    }

    public int getActiveIndex()
    {
        return activeIndex;
    }

    public void setActiveIndex(int activeIndex)
    {
        this.activeIndex = activeIndex;
    }

    // Move to the next step
    public void next()
    {
        if (activeIndex == 0)
        {
            activeIndex++;
        }
    }

    // Move to the previous step
    public void previous()
    {
        if (activeIndex > 0)
        {
            activeIndex--;
        }
    }

    @PostConstruct
    public void init()
    {
        is = FacesContext.getCurrentInstance()
                .getExternalContext()
                .getResourceAsStream("/resources/kaggle.csv");

    }

    /**
     * Invalidate the session when the index is deleted.
     *
     */
    public void invalidateSession()
    {
        System.out.println("called invalidateSession()");
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
    }
/**Clean up memory.
 * 
 */
    @PreDestroy
    public void destroy()
    {
     
     results = null;
    fileStreamContent = null;
        ai = null;
        try
        {

            if (is != null)
            {
                is.close();
                is = null;
            }
        }
        catch (IOException e)
        {
            System.out.println(e);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            "Failed to close file kaggle.csv.",
                            e.toString()
                    )
            );
        }
        System.gc();
    }

    public AthletesInjuries getAi()
    {
        return ai;
    }

    public String getResults()
    {
        return results;
    }

    public void createNeuralNetworkTrainandTest() 
    {

        if (is == null)
        {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "File open failed", "Cannot open the training CSV file.");
            FacesContext.getCurrentInstance().addMessage(null, message);
            return;
        }
        try
        {
            this.results = ai.testAthleteInjuriesWithXGBoostBean(is);
            is.close();
            is = FacesContext.getCurrentInstance()
                    .getExternalContext()
                    .getResourceAsStream("/resources/kaggle.csv");

            String s = ai.trainAndSaveInjuryModelBean(is);
            this.results += s;
            is.close();
            this.next();

        }
        catch (Throwable e)
        {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_FATAL, "Fatal Error", e.toString());
            FacesContext.getCurrentInstance().addMessage(null, message);

        }

    }

    private void download()
    {
        try
        {
            final InputStream inputStream = FacesContext.getCurrentInstance()
                    .getExternalContext()
                    .getResourceAsStream("/resources/kaggle.csv");
            if (inputStream == null)
            {
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "File download failed", "Cannot access the training CSV file.");
                FacesContext.getCurrentInstance().addMessage(null, message);
                return;
            }

            String mime = "application/csv";
            DefaultStreamedContent.Builder b = DefaultStreamedContent.builder().name("kaggle.csv");
            b = b.contentType(mime);
            b.stream(new SerializableSupplier<InputStream>()
            {
                @Override
                public InputStream get()
                {
                    return inputStream;
                }
            });

            this.fileStreamContent = b.build();
        }
        catch (Throwable e)
        {

            System.out.println(e);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            "Failed to download file.",
                            e.toString()
                    )
            );
        }

    }

    public StreamedContent getFileStreamContent()
    {
        System.out.println("getFileStreamContent() called");
        download();
        return fileStreamContent;
    }

    public void gotoPrediction()
    {
        FacesContext fc = FacesContext.getCurrentInstance();
        String page = "predictor";
        ConfigurableNavigationHandler handler = (ConfigurableNavigationHandler) fc.getApplication().getNavigationHandler();
        handler.performNavigation(page + "?faces-redirect=true");
    }
}
