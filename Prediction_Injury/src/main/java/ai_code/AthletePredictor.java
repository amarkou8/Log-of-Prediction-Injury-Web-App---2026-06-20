package ai_code;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import ml.dmlc.xgboost4j.java.Booster;
import ml.dmlc.xgboost4j.java.DMatrix;
import ml.dmlc.xgboost4j.java.XGBoost;

/**
 ** Basketball 0
 * Track 1
 * Soccer 2
 * Other 3
 multimodal_sports_injury_dataset.csv
├── Metadata (7 columns)
│   ├── athlete_id (1-156)
│   ├── session_id (1-100+)
│   ├── sport_type (Soccer, Basketball, Track, Other)
│   ├── gender (Male, Female)
│   ├── age (18-35 years)
│   └── bmi (18.5-28.3)
│
├── Physiological (6 features)
│   ├── heart_rate (40-180 bpm)
│   ├── body_temperature (35.8-39.2 °C)
│   ├── hydration_level (45-100%)
│   ├── sleep_quality (2-10 score)
│   ├── recovery_score (25-98 score)
│   └── stress_level (0.1-0.95 a.u.)
│
├── Biomechanical (8 features)
│   ├── muscle_activity (10-850 μV)
│   ├── joint_angles (45-175 degrees)
│   ├── gait_speed (0.8-3.5 m/s)
│   ├── cadence (50-200 steps/min)
│   ├── step_count (2000-15000)
│   ├── jump_height (0.15-0.85 m)
│   ├── ground_reaction_force (800-2800 N)
│   └── range_of_motion (60-180 degrees)
│
├── Environmental (4 features)
│   ├── ambient_temperature (15-38 °C)
│   ├── humidity (30-85%)
│   ├── altitude (0-1200 m)
│   └── playing_surface (0-4 categorical)
│
├── Workload (4 features)
│   ├── training_intensity (2-10 RPE)
│   ├── training_duration (30-180 min)
│   ├── training_load (150-1800 a.u.)
│   └── fatigue_index (15-85 score)
│
└── Target Variable
    └── injury_occurred (0=Healthy, 1=Low Risk, 2=Injured)

 */
public class AthletePredictor implements Serializable
{

    private Booster trainedBooster;

    // Load the optimized binary file into memory instantly
    public AthletePredictor(String modelPath) throws Throwable
    {
        this.trainedBooster = XGBoost.loadModel(modelPath);
    }

    /**
     * Accepts a raw 26-element float array of an athlete's condition metrics
     * and returns the exact predicted risk class using your optimized
     * thresholds.
     */
    public int predictAthleteRisk(float[] physiologicalMetrics26)
            throws Throwable
    {
        if (physiologicalMetrics26.length != 26)
        {
            throw new IllegalArgumentException("Expected exactly 26 health metrics.");
        }

        // 1. Convert the single row array into an inference DMatrix wrapper
        DMatrix rowMatrix = new DMatrix(physiologicalMetrics26, 1, 26, Float.NaN);

        // 2. Fetch raw soft-max probability array from the C++ JNI engine
        float[][] predictions = this.trainedBooster.predict(rowMatrix);
        double p0 = predictions[0][0];
        double p1 = predictions[0][1];
        double p2 = predictions[0][2];

        // 3. Apply your precise cross-validated multiplier thresholds
        double score0 = p0 * 1.0;
        double score1 = p1 * 1.6; // Verified 1.6 threshold offset
        double score2 = p2 * 1.3; // Verified 1.3 threshold offset

        // 4. Map the highest score to its respective production output label
        int finalClassChoice = 0;

        double maxScore = score0;

        if (score1 > maxScore)
        {
            maxScore = score1;
            finalClassChoice = 1; // 1 = Low Risk
        }
        if (score2 > maxScore)
        {
            finalClassChoice = 2; // 2 = Injured
        }
        if (finalClassChoice == 0)
        {
            System.out.println(" 0 = Healthy ");
        }
        else if (finalClassChoice == 1)
        {
            System.out.println(" 1 = Low Risk ");
        }
        else
        {
            System.out.println(" 2 = Injured ");
        }

        return finalClassChoice;
    }

    public static List<Float[]> createTestData()
    {
        int MAX_ROWS = 10;

        List<Float[]> dataList = new ArrayList<>();

        for (int i = 0; i < MAX_ROWS; ++i)
        {
            Float[] physiologicalMetrics26 = new Float[26];
            for (int j = 0; j < 26; ++j)
            {
                physiologicalMetrics26[0] = (float) (40 + (Math.random() * 120)); //heart_rate 40-180
                physiologicalMetrics26[1] = (float) (35.8 + (Math.random() * 3.4)); //body_temperature (35.8-39.2 °C)
                physiologicalMetrics26[2] = (float) (45 + (Math.random() * 55)); //hydration_level (45-100%)
                physiologicalMetrics26[3] = (float) (2 + (Math.random() * 8)); //sleep_quality (2-10 score)
                physiologicalMetrics26[4] = (float) (25 + (Math.random() * 73)); //recovery_score (25-98 score)
                physiologicalMetrics26[5] = (float) (0.1 + (Math.random() * 0.85)); //stress_level (0.1-0.95 a.u.)
                physiologicalMetrics26[6] = (float) (10 + (Math.random() * 840)); //muscle_activity (10-850 μV)
                physiologicalMetrics26[7] = (float) (45 + (Math.random() * 130)); //joint_angles (45-175 degrees)
                physiologicalMetrics26[8] = (float) (0.8 + (Math.random() * 2.7)); //gait_speed (0.8-3.5 m/s)
                physiologicalMetrics26[9] = (float) (50 + (Math.random() * 150)); //cadence (50-200 steps/min)
                physiologicalMetrics26[10] = (float) (2000 + (Math.random() * 13000)); //step_count (2000-15000)
                physiologicalMetrics26[11] = (float) (0.15 + (Math.random() * 0.70)); //jump_height (0.15-0.85 m)
                physiologicalMetrics26[12] = (float) (800 + (Math.random() * 2000)); //ground_reaction_force (800-2800 N)
                physiologicalMetrics26[13] = (float) (60 + (Math.random() * 120)); //range_of_motion (60-180 degrees)
                physiologicalMetrics26[14] = (float) (15 + (Math.random() * 23)); //ambient_temperature (15-38 °C)
                physiologicalMetrics26[15] = (float) (30 + (Math.random() * 55)); //humidity (30-85%)
                physiologicalMetrics26[16] = (float) (0 + (Math.random() * 1200)); //altitude (0-1200 m)
                physiologicalMetrics26[17] = (float) (0 + ((int) (Math.random() * 5)));  //playing_surface (0-4 categorical)
                physiologicalMetrics26[18] = (float) (2 + (Math.random() * 8));  //training_intensity (2-10 RPE)
                physiologicalMetrics26[19] = (float) (20 + (Math.random() * 150));  //training_duration (30-180 min)
                physiologicalMetrics26[20] = (float) (150 + (Math.random() * 1650));  //training_load (150-1800 a.u.)
                physiologicalMetrics26[21] = (float) (15 + (Math.random() * 70));  //fatigue_index (15-85 score)
                physiologicalMetrics26[22] = (float) (0 + (int) (Math.random() * 4));  //sport_type (0 basketball, 1 track, 2 soccer, 3 other)
                physiologicalMetrics26[23] = (float) (0 + (int) (Math.random() * 2)); //gender 0 female, 1 male
                physiologicalMetrics26[24] = (float) (18 + (Math.random() * 23));  //age (18-35 years)
                physiologicalMetrics26[25] = (float) (18.5 + (Math.random() * 9.8));  //bmi (18.5-28.3)    
            }
            dataList.add(physiologicalMetrics26);
        }

        return dataList;

    }

    public static void main(String[] args) throws Throwable
    {
        System.out.println("Testing");

        System.out.println("-----------------------------------");
        System.out.println("-----------------------------------");
        System.out.println("-----------------------------------");

        AthletePredictor ap = new AthletePredictor("athlete_injury_xgb_model.bin");
        float[] physiologicalMetrics26 = new float[26];

        physiologicalMetrics26[0] = 70; //heart_rate 40-180
        physiologicalMetrics26[1] = 36; //body_temperature (35.8-39.2 °C)
        physiologicalMetrics26[2] = 88.8f; //hydration_level (45-100%)
        physiologicalMetrics26[3] = 9; //sleep_quality (2-10 score)
        physiologicalMetrics26[4] = 90; //recovery_score (25-98 score)
        physiologicalMetrics26[5] = 0.4f; //stress_level (0.1-0.95 a.u.)
        physiologicalMetrics26[6] = 500; //muscle_activity (10-850 μV)
        physiologicalMetrics26[7] = 90.8f; //joint_angles (45-175 degrees)
        physiologicalMetrics26[8] = 3.2f; //gait_speed (0.8-3.5 m/s)
        physiologicalMetrics26[9] = 156; //cadence (50-200 steps/min)
        physiologicalMetrics26[10] = 8888; //step_count (2000-15000)
        physiologicalMetrics26[11] = 0.581f; //jump_height (0.15-0.85 m)
        physiologicalMetrics26[12] = 1453.3f; //ground_reaction_force (800-2800 N)
        physiologicalMetrics26[13] = 121.3f; //range_of_motion (60-180 degrees)
        physiologicalMetrics26[14] = 23.8f; //ambient_temperature (15-38 °C)
        physiologicalMetrics26[15] = 55.5f; //humidity (30-85%)
        physiologicalMetrics26[16] = 250.0f; //altitude (0-1200 m)
        physiologicalMetrics26[17] = 3; //playing_surface (0-4 categorical)
        physiologicalMetrics26[18] = 8.8f; //training_intensity (2-10 RPE)
        physiologicalMetrics26[19] = 90.5f; //training_duration (30-180 min)
        physiologicalMetrics26[20] = 753.1f; //training_load (150-1800 a.u.)
        physiologicalMetrics26[21] = 54.4f; //fatigue_index (15-85 score)
        physiologicalMetrics26[22] = 1; //sport_type (0 basketball, 1 track, 2 soccer, 3 other)
        physiologicalMetrics26[23] = 1; //gender 0 female, 1 male
        physiologicalMetrics26[24] = 23.0f; //age (18-35 years)
        physiologicalMetrics26[25] = 24.6f; //bmi (18.5-28.3)

       List<Float[]> list =  createTestData();       
       for ( int i = 0; i < list.size(); ++i )
       {
           
          Float[] data =  list.get(i);
          float[] ar = new float[data.length];
          for ( int j=0; j < data.length; ++j)
          {
             ar[j] = data[j];
          }
          
          ap.predictAthleteRisk(ar);

       }
    }
}
