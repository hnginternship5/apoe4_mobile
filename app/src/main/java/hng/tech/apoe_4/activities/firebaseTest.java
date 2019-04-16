package hng.tech.apoe_4.activities;

public class firebaseTest {

    private String questionTitle;
    private String answerOptions;
    private int imgId;

    public firebaseTest(String question, String answers, int imgId) {
        this.questionTitle = question;
        this.answerOptions = answers;
        this.imgId = imgId;
    }

    public String getQuestionTitle() {
        return questionTitle;
    }

    public void setQuestionTitle(String questionTitle) {
        this.questionTitle = questionTitle;
    }

    public int getImgId() {
        return imgId;
    }

    public void setImgId(int imgId) {
        this.imgId = imgId;
    }

    public String getAnswerOptions() {
        return answerOptions;
    }

    public void setAnswerOptions(String answerOptions) {
        {
            this.answerOptions = answerOptions;
        }
    }
}



    // Declaring views
//    TextView question_title;
//
//    // Firebase components
//    FirebaseDatabase firebaseDatabase;
//    DatabaseReference databaseReference;
//
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.question);

//        // Initializing views
//        question_title = findViewById(R.id.question_title);
//
//        // Initializing Firebase components
//        firebaseDatabase = FirebaseDatabase.getInstance();
//
//        databaseReference = firebaseDatabase.getReference();
//
//        // Setting time constraints
//        Date currentTime = Calendar.getInstance().getTime();



