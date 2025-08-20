<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
    <title>Candidate Details</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background: #f4f7f9;
            margin: 0;
            padding: 0;
        }

        h2 {
            text-align: center;
            color: #333;
            margin-top: 20px;
        }

        form {
            max-width: 900px;
            margin: 20px auto;
            background: #fff;
            padding: 25px;
            border-radius: 12px;
            box-shadow: 0px 4px 12px rgba(0,0,0,0.1);
        }

        .candidate-card {
            background: #fafafa;
            border: 1px solid #ddd;
            border-radius: 10px;
            padding: 20px;
            margin-bottom: 25px;
            box-shadow: 0px 2px 6px rgba(0,0,0,0.05);
        }

        .candidate-card h3 {
            margin-top: 0;
            color: #007BFF;
            border-bottom: 2px solid #eee;
            padding-bottom: 5px;
        }

        label {
            font-weight: bold;
            display: block;
            margin-top: 12px;
            margin-bottom: 5px;
            color: #555;
        }

        input[type="text"],
        input[type="date"],
        textarea,
        input[type="file"] {
            width: 100%;
            padding: 10px;
            border: 1px solid #ccc;
            border-radius: 6px;
            font-size: 14px;
            margin-bottom: 10px;
            transition: 0.3s;
        }

        input:focus,
        textarea:focus {
            border-color: #007BFF;
            outline: none;
            box-shadow: 0px 0px 5px rgba(0,123,255,0.3);
        }

        textarea {
            height: 80px;
            resize: vertical;
        }

        input[type="submit"] {
            background: #007BFF;
            color: white;
            font-size: 16px;
            font-weight: bold;
            padding: 12px 20px;
            border: none;
            border-radius: 8px;
            cursor: pointer;
            transition: 0.3s;
            width: 100%;
            margin-top: 20px;
        }

        input[type="submit"]:hover {
            background: #0056b3;
        }
    </style>

  <script>
    function calculateAge(dobValue) {
        let dob = new Date(dobValue);
        let today = new Date();

        let age = today.getFullYear() - dob.getFullYear();
        let month = today.getMonth() - dob.getMonth();

        if (month < 0 || (month === 0 && today.getDate() < dob.getDate())) {
            age--; // adjust if birthday not yet reached
        }
        return age;
    }

    function validateForm(totalCandidates) {
        for (let i = 1; i <= totalCandidates; i++) {
            let dobField = document.getElementById("dob" + i);
            let hiddenAgeField = document.getElementById("age" + i);

            if (!dobField || !dobField.value) {
                alert("Please enter DOB for Candidate " + i);
                dobField.focus();
                return false;
            }

            let age = calculateAge(dobField.value);

            if (age < 18) {
                alert("Candidate " + i + " must be at least 18 years old.");
                dobField.focus();
                return false;
            }

            // ✅ Store calculated age into hidden field
            hiddenAgeField.value = age;
        }
        return true;
    }
</script>


</head>
<body>
    <h2>Enter Candidate Details</h2>
    <%
      Integer NoOfCandidates = (Integer)request.getAttribute("noOfCandidates");
      Integer ElectionId = (Integer)request.getAttribute("electionId");
      String refCode = (String) request.getAttribute("refcode");
      if(NoOfCandidates!=null){
    %>
   
       
    <form action="CandidateServlet" method="post" enctype="multipart/form-data" 
          onsubmit="return validateForm(<%= NoOfCandidates %>)">
        <input type="hidden" name="electionId" value="<%= ElectionId %>">
        <input type="hidden" name="totalCandidates" value="<%= NoOfCandidates %>">
		<input type="hidden" name="refCode" value="<%= refCode %>">
		
        <%
       
        for (int i=1; i<=NoOfCandidates; i++) { %>
            <div class="candidate-card">
                <h3>Candidate <%= i %></h3>

                <label for="name<%= i %>">Name:</label>
                <input type="text" id="name<%= i %>" name="name<%= i %>" required>

                <label for="education<%= i %>">Education:</label>
                <input type="text" id="education<%= i %>" name="education<%= i %>" required>

                <label for="address<%= i %>">Address:</label>
                <input type="text" id="address<%= i %>" name="address<%= i %>" required>

                <label for="dob<%= i %>">Date of Birth:</label>
				<input type="date" id="dob<%= i %>" name="dob<%= i %>" required>

				<!-- Hidden Age field (auto-filled by JS before submit) -->
				<input type="hidden" id="age<%= i %>" name="age<%= i %>">


                <label for="about<%= i %>">About:</label>
                <textarea id="about<%= i %>" name="about<%= i %>" required></textarea>

                <label for="photo<%= i %>">Photo:</label>
                <input type="file" id="photo<%= i %>" name="photo<%= i %>" accept="image/*" required>
            </div>
<%} %>
        <input type="submit" value="Save Candidates">
   
    </form>
   <% }else{ %>
        <div class="candidate-card">
        		<h2 style="color:red;">Candidate is null!!</h2>
        </div>
   <% }%>
</body>
</html>
