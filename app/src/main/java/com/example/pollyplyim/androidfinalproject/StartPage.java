package com.example.pollyplyim.androidfinalproject;


import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.view.View.OnClickListener;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import java.net.URL;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;


public class StartPage extends AppCompatActivity {
    final Context context = this;
    private StudentDAO studentDAO;
    ProgressDialog pDialog;
    NodeList studentlist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_page);
        studentDAO = new StudentDAO(this);

        boolean isFirstRun = getSharedPreferences("PREFERENCE", MODE_PRIVATE)
                .getBoolean("isFirstRun", true);
        if (isFirstRun) {
            String urlString = "http://www.algonquinstudents.ca/~yim00008/android/students.xml";
            new LoadStudentTask().execute(urlString);
        }

        getSharedPreferences("PREFERENCE", MODE_PRIVATE).edit()
                .putBoolean("isFirstRun", false).commit();

        Button buttonEnter = (Button) findViewById(R.id.student_list_button);
        buttonEnter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StartPage.this, MainActivity.class);
                startActivity(intent);
            }
        });

        Button buttonSummary = (Button) findViewById(R.id.summary_button);
        buttonSummary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StartPage.this, Summary.class);
                startActivity(intent);
            }
        });

        Button buttonInfo = (Button) findViewById(R.id.info_button);
        buttonInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // custom dialog
                final Dialog dialog = new Dialog(context);
                dialog.setContentView(R.layout.fragment_info);
                Button dialogButton = (Button) dialog.findViewById(R.id.button_exit);
                // if button is clicked, close the custom dialog
                dialogButton.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                dialog.show();
            }
        });

    }

    public class LoadStudentTask extends AsyncTask<String, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Create a progressbar
            pDialog = new ProgressDialog(StartPage.this);
            // Set progressbar message
            pDialog.setMessage("Loading...");
            pDialog.setIndeterminate(false);
            // Show progressbar
            pDialog.show();
        }

        @Override
        protected Void doInBackground(String... Url) {
            try {
                URL url = new URL(Url[0]);
                DocumentBuilderFactory dbf = DocumentBuilderFactory
                        .newInstance();
                DocumentBuilder db = dbf.newDocumentBuilder();
                // Download the XML file
                Document doc = db.parse(new InputSource(url.openStream()));
                doc.getDocumentElement().normalize();
                // Locate the Tag Name
                studentlist = doc.getElementsByTagName("Student");

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;

        }

        @Override
        protected void onPostExecute(Void args) {
            for (int temp = 0; temp < studentlist.getLength(); temp++) {
                Node nNode = studentlist.item(temp);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    Student s = new Student();
                    s.setName(getNode("Name", eElement));
                    s.setGpa(Double.parseDouble(getNode("Gpa",eElement)));
                    studentDAO.save(s);
                }
            }
            // Close progressbar
            pDialog.dismiss();
        }


    // getNode function
        private String getNode(String sTag, Element eElement) {
            NodeList nlList = eElement.getElementsByTagName(sTag).item(0)
                .getChildNodes();
            Node nValue = (Node) nlList.item(0);
            return nValue.getNodeValue();
        }
    }
}
