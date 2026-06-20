/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package beans;

import ai_code.AthletePredictor;
import jakarta.annotation.PreDestroy;
import jakarta.inject.Named;
import jakarta.faces.application.ConfigurableNavigationHandler;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.event.AjaxBehaviorEvent;
import jakarta.faces.event.ValueChangeEvent;
import jakarta.faces.view.ViewScoped;
import java.io.Serializable;
import org.primefaces.PrimeFaces;

/**
 *
 * @author asdv5
 */
@Named(value = "predictorBean")
@ViewScoped
public class PredictorBean implements Serializable
{

    private AthletePredictor ap;

    private Float stress;
    private Float recoveryScore;
    private Float heartRate;
    private Float bodyTemperature;
    private Float hydrationLevel;
    private Float sleepQuality;
    private Float age;
    private Float bmi;
    private String gender;
    private String sportType;
    private Float muscleActivity;
    private Float jointAngles;
    private Float gaitSpeed;
    private Float cadence;
    private Float stepCount;
    private Float jumpHeight;
    private Float groundReactionForce;
    private Float rangeOfMotion;
    private Float ambientTemperature;
    private Float humidity;
    private Float altitude;
    private String playingSurface;
    private Float trainingIntensity;
    private Float trainingDuration;
    private Float trainingLoad;
    private Float fatigueIndex;

    /**
     * Default constructor
     */
    public PredictorBean() throws Throwable
    {
        ap = new AthletePredictor("athlete_injury_xgb_model.bin");
        resetToDefaultValues();
    }

    public void resetToDefaultValues()
    {
        gender = "MALE";
        sportType = "TRACK";
        playingSurface = "TURF";
        
        
        stress = 0.12f;
        recoveryScore = 29f;
        heartRate = 45f;
        bodyTemperature = 36.0f;
        hydrationLevel = 72f;
        sleepQuality = 3.0f;
        age = 25f;
        bmi = 23.0f;

        muscleActivity = 420f;
        jointAngles = 110f;
        gaitSpeed = 3f;
        cadence = 125f;
        stepCount = 8500f;
        jumpHeight = 0.4f;
        groundReactionForce = 1800f;
        rangeOfMotion = 120f;
        ambientTemperature = 26.5f;
        humidity = 57f;
        altitude = 600f;
        trainingIntensity = 6f;
        trainingDuration = 105f;
        trainingLoad = 975f;
        fatigueIndex = 40f;
    }

    public boolean anyVarsNotInitialized()
    {
        if (stress == null)
        {
            return true;
        }
        if (recoveryScore == null)
        {
            return true;
        }
        if (heartRate == null)
        {
            return true;
        }

        if (bodyTemperature == null)
        {
            return true;
        }
        if (hydrationLevel == null)
        {
            return true;
        }
        if (sleepQuality == null)
        {
            return true;
        }
        if (age == null)
        {
            return true;
        }
        if (bmi == null)
        {
            return true;
        }
        if (gender == null)
        {
            return true;
        }
        if (sportType == null)
        {
            return true;
        }
        if (muscleActivity == null)
        {
            return true;
        }
        if (jointAngles == null)
        {
            return true;
        }
        if (gaitSpeed == null)
        {
            return true;
        }
        if (cadence == null)
        {
            return true;
        }
        if (stepCount == null)
        {
            return true;
        }
        if (jumpHeight == null)
        {
            return true;
        }
        if (groundReactionForce == null)
        {
            return true;
        }
        if (rangeOfMotion == null)
        {
            return true;
        }
        if (ambientTemperature == null)
        {
            return true;
        }
        if (humidity == null)
        {
            return true;
        }
        if (altitude == null)
        {
            return true;
        }
        if (playingSurface == null)
        {
            return true;
        }
        if (trainingIntensity == null)
        {
            return true;
        }
        if (trainingDuration == null)
        {
            return true;
        }
        if (trainingLoad == null)
        {
            return true;
        }
        if (fatigueIndex == null)
        {
            return true;
        }

        return false;
    }

    public void predictRisk() throws Throwable
    {
        if (anyVarsNotInitialized())
        {

            return;
        }

        float[] physiologicalMetrics26 = new float[26];

        physiologicalMetrics26[0] = this.heartRate; //heart_rate 40-180
        physiologicalMetrics26[1] = this.bodyTemperature; //body_temperature (35.8-39.2 °C)
        physiologicalMetrics26[2] = this.hydrationLevel; //hydration_level (45-100%)
        physiologicalMetrics26[3] = this.sleepQuality; //sleep_quality (2-10 score)
        physiologicalMetrics26[4] = this.recoveryScore; //recovery_score (25-98 score)
        physiologicalMetrics26[5] = this.stress; //stress_level (0.1-0.95 a.u.)
        physiologicalMetrics26[6] = this.muscleActivity; //muscle_activity (10-850 μV)
        physiologicalMetrics26[7] = this.jointAngles; //joint_angles (45-175 degrees)
        physiologicalMetrics26[8] = this.gaitSpeed; //gait_speed (0.8-3.5 m/s)
        physiologicalMetrics26[9] = this.cadence; //cadence (50-200 steps/min)
        physiologicalMetrics26[10] = this.stepCount; //step_count (2000-15000)
        physiologicalMetrics26[11] = this.jumpHeight; //jump_height (0.15-0.85 m)
        physiologicalMetrics26[12] = this.groundReactionForce; //ground_reaction_force (800-2800 N)
        physiologicalMetrics26[13] = this.rangeOfMotion; //range_of_motion (60-180 degrees)
        physiologicalMetrics26[14] = this.ambientTemperature; //ambient_temperature (15-38 °C)
        physiologicalMetrics26[15] = this.humidity; //humidity (30-85%)
        physiologicalMetrics26[16] = this.altitude; //altitude (0-1200 m)
        if ("GRASS".equals(this.playingSurface))
        {
            physiologicalMetrics26[17] = 0;
        }
        else if ("TURF".equals(this.playingSurface))
        {
            physiologicalMetrics26[17] = 1;
        }
        else if ("INDOOR".equals(this.playingSurface))
        {
            physiologicalMetrics26[17] = 2;
        }
        else if ("TRACK".equals(this.playingSurface))
        {
            physiologicalMetrics26[17] = 3;
        }
        else  //OTHER
        {
            physiologicalMetrics26[17] = 4;
        }
        physiologicalMetrics26[18] = this.trainingIntensity; //training_intensity (2-10 RPE)
        physiologicalMetrics26[19] = this.trainingDuration; //training_duration (30-180 min)
        physiologicalMetrics26[20] = this.trainingLoad; //training_load (150-1800 a.u.)
        physiologicalMetrics26[21] = this.fatigueIndex; //fatigue_index (15-85 score)
        if ("BASKETBALL".equals(this.sportType))
        {
            physiologicalMetrics26[22] = 0;//sport_type (0 basketball, 1 track, 2 soccer, 3 other)
        }
        else if ("TRACK".equals(this.sportType))
        {
            physiologicalMetrics26[22] = 1;//sport_type (0 basketball, 1 track, 2 soccer, 3 other)
        }

        else if ("SOCCER".equals(this.sportType))
        {
            physiologicalMetrics26[22] = 2;//sport_type (0 basketball, 1 track, 2 soccer, 3 other)
        }
        else//OTHER
        {
            physiologicalMetrics26[22] = 3;//sport_type (0 basketball, 1 track, 2 soccer, 3 other)
        }

        if ("FEMALE".equals(this.gender))
        {
            physiologicalMetrics26[23] = 0;//gender 0 female, 1 male
        }
        else
        {
            physiologicalMetrics26[23] = 1;//gender 0 female, 1 male
        }
        physiologicalMetrics26[24] = this.age; //age (18-35 years)
        physiologicalMetrics26[25] = this.bmi; //bmi (18.5-28.3)

        int prediction = ap.predictAthleteRisk(physiologicalMetrics26);

        String msg = "";
        if (prediction == 0)
        {
            msg = "Healthy, good to go!";
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,
                    "&#x2705; " + "Prediction",
                    msg);
            PrimeFaces.current().dialog().showMessageDynamic(
                    message, false);
            PrimeFaces.current().executeScript("$('.ui-dialog:visible').addClass('green-dialog');");
        }
        else if (prediction == 1)
        {
            msg = "Low risk...";
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,
                    "&#x26A0; " + "Prediction",
                    msg);
            PrimeFaces.current().dialog().showMessageDynamic(
                    message, false);
            PrimeFaces.current().executeScript("$('.ui-dialog:visible').addClass('blue-dialog');");

        }
        else if (prediction == 2)
        {
            msg = "High risk, possible injury.";
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,
                    "&#x1F3E5; " + "Prediction",
                    msg);
            PrimeFaces.current().dialog().showMessageDynamic(
                    message, false);
            PrimeFaces.current().executeScript("$('.ui-dialog:visible').addClass('red-dialog');");

        }
        else
        {
            throw new RuntimeException("Bug, this line should never execute: public void predictRisk() ");
        }

    }

    public void onValueChangedSleepQuality(ValueChangeEvent event)
    {
        // Retrieve the old and new values from the event
        Float oldValue = (Float) event.getOldValue();
        Float newValue = (Float) event.getNewValue();

        // Add a message to be displayed by the 'id_growl' component
        FacesContext context = FacesContext.getCurrentInstance();
        String msgText = String.format("Sleep quality changed from %s to %s", oldValue, newValue);
        context.addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO,
                        "Value Updated", msgText));
    }

    public void onValueChangedHydrationLevel(ValueChangeEvent event)
    {
        // Retrieve the old and new values from the event
        Float oldValue = (Float) event.getOldValue();
        Float newValue = (Float) event.getNewValue();

        // Add a message to be displayed by the 'id_growl' component
        FacesContext context = FacesContext.getCurrentInstance();
        String msgText = String.format("Hydration level changed from %s to %s", oldValue, newValue);
        context.addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO,
                        "Value Updated", msgText));
    }

    public void onValueChangedAge(ValueChangeEvent event)
    {
        // Retrieve the old and new values from the event
        Float oldValue = (Float) event.getOldValue();
        Float newValue = (Float) event.getNewValue();

        // Add a message to be displayed by the 'id_growl' component
        FacesContext context = FacesContext.getCurrentInstance();
        String msgText = String.format("Age changed from %s to %s", oldValue, newValue);
        context.addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO,
                        "Value Updated", msgText));
    }

    public void onClickedGender(AjaxBehaviorEvent event)
    {

        // Add a message to be displayed by the 'id_growl' component
        FacesContext context = FacesContext.getCurrentInstance();
        String msgText = String.format("Gender  %s was clicked", this.gender);
        context.addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO,
                        "Value Updated", msgText));
    }

    public void onClickedSportType(AjaxBehaviorEvent event)
    {

        // Add a message to be displayed by the 'id_growl' component
        FacesContext context = FacesContext.getCurrentInstance();
        String msgText = String.format("Sport  %s was clicked", this.sportType);
        context.addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO,
                        "Value Updated", msgText));
    }

    public void onValueChangedBmi(ValueChangeEvent event)
    {
        // Retrieve the old and new values from the event
        Float oldValue = (Float) event.getOldValue();
        Float newValue = (Float) event.getNewValue();

        // Add a message to be displayed by the 'id_growl' component
        FacesContext context = FacesContext.getCurrentInstance();
        String msgText = String.format("BMI changed from %s to %s", oldValue, newValue);
        context.addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO,
                        "Value Updated", msgText));
    }

    /**
     * Listener for the valueChangeListener attribute
     *
     * @param event
     */
    public void onValueChangedStress(ValueChangeEvent event)
    {
        // Retrieve the old and new values from the event
        Float oldValue = (Float) event.getOldValue();
        Float newValue = (Float) event.getNewValue();

        // Add a message to be displayed by the 'id_growl' component
        FacesContext context = FacesContext.getCurrentInstance();
        String msgText = String.format("Stress level changed from %s to %s", oldValue, newValue);
        context.addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO,
                        "Value Updated", msgText));
    }

    public void onValueChangedBodyTemperature(ValueChangeEvent event)
    {
        // Retrieve the old and new values from the event
        Float oldValue = (Float) event.getOldValue();
        Float newValue = (Float) event.getNewValue();

        // Add a message to be displayed by the 'id_growl' component
        FacesContext context = FacesContext.getCurrentInstance();
        String msgText = String.format("Body temperature changed from %s to %s", oldValue, newValue);
        context.addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO,
                        "Value Updated", msgText));
    }

    public void onValueChangedRecoveryScore(ValueChangeEvent event)
    {
        // Retrieve the old and new values from the event
        Float oldValue = (Float) event.getOldValue();
        Float newValue = (Float) event.getNewValue();

        // Add a message to be displayed by the 'id_growl' component
        FacesContext context = FacesContext.getCurrentInstance();
        String msgText = String.format("Recovery level changed from %s to %s", oldValue, newValue);
        context.addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO,
                        "Value Updated", msgText));
    }

    public void onValueChangedHeartRate(ValueChangeEvent event)
    {
        // Retrieve the old and new values from the event
        Float oldValue = (Float) event.getOldValue();
        Float newValue = (Float) event.getNewValue();

        // Add a message to be displayed by the 'id_growl' component
        FacesContext context = FacesContext.getCurrentInstance();
        String msgText = String.format("Heart rate changed from %s to %s", oldValue, newValue);
        context.addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO,
                        "Value Updated", msgText));
    }

    public void onValueChangedMuscleActivity(ValueChangeEvent event)
    {
        // Retrieve the old and new values from the event
        Float oldValue = (Float) event.getOldValue();
        Float newValue = (Float) event.getNewValue();

        // Add a message to be displayed by the 'id_growl' component
        FacesContext context = FacesContext.getCurrentInstance();
        String msgText = String.format("Muscle activity changed from %s to %s", oldValue, newValue);
        context.addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO,
                        "Value Updated", msgText));
    }

    public void onValueChangedJointAngles(ValueChangeEvent event)
    {
        // Retrieve the old and new values from the event
        Float oldValue = (Float) event.getOldValue();
        Float newValue = (Float) event.getNewValue();

        // Add a message to be displayed by the 'id_growl' component
        FacesContext context = FacesContext.getCurrentInstance();
        String msgText = String.format("Joint angles changed from %s to %s", oldValue, newValue);
        context.addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO,
                        "Value Updated", msgText));
    }

    public void onValueChangedGaitSpeed(ValueChangeEvent event)
    {
        // Retrieve the old and new values from the event
        Float oldValue = (Float) event.getOldValue();
        Float newValue = (Float) event.getNewValue();

        // Add a message to be displayed by the 'id_growl' component
        FacesContext context = FacesContext.getCurrentInstance();
        String msgText = String.format("Gait speed changed from %s to %s", oldValue, newValue);
        context.addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO,
                        "Value Updated", msgText));
    }

    public void onValueChangedCadence(ValueChangeEvent event)
    {
        // Retrieve the old and new values from the event
        Float oldValue = (Float) event.getOldValue();
        Float newValue = (Float) event.getNewValue();

        // Add a message to be displayed by the 'id_growl' component
        FacesContext context = FacesContext.getCurrentInstance();
        String msgText = String.format("Cadence changed from %s to %s", oldValue, newValue);
        context.addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO,
                        "Value Updated", msgText));
    }

    public void onValueChangedStepCount(ValueChangeEvent event)
    {
        // Retrieve the old and new values from the event
        Float oldValue = (Float) event.getOldValue();
        Float newValue = (Float) event.getNewValue();

        // Add a message to be displayed by the 'id_growl' component
        FacesContext context = FacesContext.getCurrentInstance();
        String msgText = String.format("Step count changed from %s to %s", oldValue, newValue);
        context.addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO,
                        "Value Updated", msgText));
    }

    public void onValueChangedJumpHeight(ValueChangeEvent event)
    {
        // Retrieve the old and new values from the event
        Float oldValue = (Float) event.getOldValue();
        Float newValue = (Float) event.getNewValue();

        // Add a message to be displayed by the 'id_growl' component
        FacesContext context = FacesContext.getCurrentInstance();
        String msgText = String.format("Jump height changed from %s to %s", oldValue, newValue);
        context.addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO,
                        "Value Updated", msgText));
    }

    public void onValueChangedGroundReactionForce(ValueChangeEvent event)
    {
        // Retrieve the old and new values from the event
        Float oldValue = (Float) event.getOldValue();
        Float newValue = (Float) event.getNewValue();

        // Add a message to be displayed by the 'id_growl' component
        FacesContext context = FacesContext.getCurrentInstance();
        String msgText = String.format("Ground reaction force changed from %s to %s", oldValue, newValue);
        context.addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO,
                        "Value Updated", msgText));
    }

    public void onValueChangedRangeOfMotion(ValueChangeEvent event)
    {
        // Retrieve the old and new values from the event
        Float oldValue = (Float) event.getOldValue();
        Float newValue = (Float) event.getNewValue();

        // Add a message to be displayed by the 'id_growl' component
        FacesContext context = FacesContext.getCurrentInstance();
        String msgText = String.format("Range of motion changed from %s to %s", oldValue, newValue);
        context.addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO,
                        "Value Updated", msgText));
    }

    public void onValueChangedAmbientTemperature(ValueChangeEvent event)
    {
        // Retrieve the old and new values from the event
        Float oldValue = (Float) event.getOldValue();
        Float newValue = (Float) event.getNewValue();

        // Add a message to be displayed by the 'id_growl' component
        FacesContext context = FacesContext.getCurrentInstance();
        String msgText = String.format("Ambient temperature changed from %s to %s", oldValue, newValue);
        context.addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO,
                        "Value Updated", msgText));
    }

    public void onValueChangedHumidity(ValueChangeEvent event)
    {
        // Retrieve the old and new values from the event
        Float oldValue = (Float) event.getOldValue();
        Float newValue = (Float) event.getNewValue();

        // Add a message to be displayed by the 'id_growl' component
        FacesContext context = FacesContext.getCurrentInstance();
        String msgText = String.format("Humidity changed from %s to %s", oldValue, newValue);
        context.addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO,
                        "Value Updated", msgText));
    }

    public void onValueChangedAltitude(ValueChangeEvent event)
    {
        // Retrieve the old and new values from the event
        Float oldValue = (Float) event.getOldValue();
        Float newValue = (Float) event.getNewValue();

        // Add a message to be displayed by the 'id_growl' component
        FacesContext context = FacesContext.getCurrentInstance();
        String msgText = String.format("Altitude changed from %s to %s", oldValue, newValue);
        context.addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO,
                        "Value Updated", msgText));
    }

    public void onClickedPlayingSurface(AjaxBehaviorEvent event)
    {

        // Add a message to be displayed by the 'id_growl' component
        FacesContext context = FacesContext.getCurrentInstance();
        String msgText = String.format("Playing surface  %s was clicked.", this.playingSurface);
        context.addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO,
                        "Value Updated", msgText));
    }

    public void onValueChangedTrainingDuration(ValueChangeEvent event)
    {
        // Retrieve the old and new values from the event
        Float oldValue = (Float) event.getOldValue();
        Float newValue = (Float) event.getNewValue();

        // Add a message to be displayed by the 'id_growl' component
        FacesContext context = FacesContext.getCurrentInstance();
        String msgText = String.format("Training duration changed from %s to %s", oldValue, newValue);
        context.addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO,
                        "Value Updated", msgText));
    }

    public void onValueChangedTrainingIntensity(ValueChangeEvent event)
    {
        // Retrieve the old and new values from the event
        Float oldValue = (Float) event.getOldValue();
        Float newValue = (Float) event.getNewValue();

        // Add a message to be displayed by the 'id_growl' component
        FacesContext context = FacesContext.getCurrentInstance();
        String msgText = String.format("Training intensity changed from %s to %s", oldValue, newValue);
        context.addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO,
                        "Value Updated", msgText));
    }

    public void onValueChangedTrainingLoad(ValueChangeEvent event)
    {
        // Retrieve the old and new values from the event
        Float oldValue = (Float) event.getOldValue();
        Float newValue = (Float) event.getNewValue();

        // Add a message to be displayed by the 'id_growl' component
        FacesContext context = FacesContext.getCurrentInstance();
        String msgText = String.format("Training load changed from %s to %s", oldValue, newValue);
        context.addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO,
                        "Value Updated", msgText));
    }

    public void onValueChangedFatigueIndex(ValueChangeEvent event)
    {
        // Retrieve the old and new values from the event
        Float oldValue = (Float) event.getOldValue();
        Float newValue = (Float) event.getNewValue();

        // Add a message to be displayed by the 'id_growl' component
        FacesContext context = FacesContext.getCurrentInstance();
        String msgText = String.format("Fatigue index changed from %s to %s", oldValue, newValue);
        context.addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO,
                        "Value Updated", msgText));
    }

    // --- Getters and Setters ---
    public Float getStress()
    {
        return stress;
    }

    public void setStress(Float stress)
    {
        this.stress = stress;
    }

    public Float getRecoveryScore()
    {
        return recoveryScore;
    }

    public void setRecoveryScore(Float recoveryScore)
    {
        this.recoveryScore = recoveryScore;
    }

    public Float getHeartRate()
    {
        return heartRate;
    }

    public void setHeartRate(Float heartRate)
    {
        this.heartRate = heartRate;
    }

    public Float getBodyTemperature()
    {
        return bodyTemperature;
    }

    public void setBodyTemperature(Float bodyTemperature)
    {
        this.bodyTemperature = bodyTemperature;
    }

    public Float getHydrationLevel()
    {
        return hydrationLevel;
    }

    public void setHydrationLevel(Float hydrationLevel)
    {
        this.hydrationLevel = hydrationLevel;
    }

    public Float getSleepQuality()
    {
        return sleepQuality;
    }

    public void setSleepQuality(Float sleepQuality)
    {
        this.sleepQuality = sleepQuality;
    }

    public Float getAge()
    {
        return age;
    }

    public void setAge(Float age)
    {
        this.age = age;
    }

    public Float getBmi()
    {
        return bmi;
    }

    public void setBmi(Float bmi)
    {
        this.bmi = bmi;
    }

    public String getGender()
    {
        return gender;
    }

    public void setGender(String gender)
    {
        this.gender = gender;
    }

    public String getSportType()
    {
        return sportType;
    }

    public void setSportType(String sportType)
    {
        this.sportType = sportType;
    }

    public Float getMuscleActivity()
    {
        return muscleActivity;
    }

    public void setMuscleActivity(Float muscleActivity)
    {
        this.muscleActivity = muscleActivity;
    }

    public Float getJointAngles()
    {
        return jointAngles;
    }

    public void setJointAngles(Float jointAngles)
    {
        this.jointAngles = jointAngles;
    }

    public Float getGaitSpeed()
    {
        return gaitSpeed;
    }

    public void setGaitSpeed(Float gaitSpeed)
    {
        this.gaitSpeed = gaitSpeed;
    }

    public Float getCadence()
    {
        return cadence;
    }

    public void setCadence(Float cadence)
    {
        this.cadence = cadence;
    }

    public Float getStepCount()
    {
        return stepCount;
    }

    public void setStepCount(Float stepCount)
    {
        this.stepCount = stepCount;
    }

    public Float getJumpHeight()
    {
        return jumpHeight;
    }

    public void setJumpHeight(Float jumpHeight)
    {
        this.jumpHeight = jumpHeight;
    }

    public Float getGroundReactionForce()
    {
        return groundReactionForce;
    }

    public void setGroundReactionForce(Float groundReactionForce)
    {
        this.groundReactionForce = groundReactionForce;
    }

    public Float getRangeOfMotion()
    {
        return rangeOfMotion;
    }

    public void setRangeOfMotion(Float rangeOfMotion)
    {
        this.rangeOfMotion = rangeOfMotion;
    }

    public Float getAmbientTemperature()
    {
        return ambientTemperature;
    }

    public void setAmbientTemperature(Float ambientTemperature)
    {
        this.ambientTemperature = ambientTemperature;
    }

    public Float getHumidity()
    {
        return humidity;
    }

    public void setHumidity(Float humidity)
    {
        this.humidity = humidity;
    }

    public Float getAltitude()
    {
        return altitude;
    }

    public void setAltitude(Float altitude)
    {
        this.altitude = altitude;
    }

    public String getPlayingSurface()
    {
        return playingSurface;
    }

    public void setPlayingSurface(String playingSurface)
    {
        this.playingSurface = playingSurface;
    }

    public Float getTrainingIntensity()
    {
        return trainingIntensity;
    }

    public void setTrainingIntensity(Float trainingIntensity)
    {
        this.trainingIntensity = trainingIntensity;
    }

    public Float getTrainingDuration()
    {
        return trainingDuration;
    }

    public void setTrainingDuration(Float trainingDuration)
    {
        this.trainingDuration = trainingDuration;
    }

    public Float getTrainingLoad()
    {
        return trainingLoad;
    }

    public void setTrainingLoad(Float trainingLoad)
    {
        this.trainingLoad = trainingLoad;
    }

    public Float getFatigueIndex()
    {
        return fatigueIndex;
    }

    public void setFatigueIndex(Float fatigueIndex)
    {
        this.fatigueIndex = fatigueIndex;
    }

    public void home()
    {
        FacesContext fc = FacesContext.getCurrentInstance();
        String page = "index";
        ConfigurableNavigationHandler handler = (ConfigurableNavigationHandler) fc.getApplication().getNavigationHandler();
        handler.performNavigation(page + "?faces-redirect=true");
    }

    @PreDestroy
    public void destroy()
    {
        this.ap = null;
        System.gc();
    }
}
