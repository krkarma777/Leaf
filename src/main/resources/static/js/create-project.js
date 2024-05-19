function searchMembers(query) {
    if (query.length < 3) {  // Start searching only if the query is at least 3 characters long
        document.getElementById('searchResults').innerHTML = '';
        return;
    }
    fetch(`/api/user/search-members?q=${encodeURIComponent(query)}`)
        .then(response => response.json())
        .then(data => {
            const resultsContainer = document.getElementById('searchResults');
            resultsContainer.innerHTML = '';  // Clear previous search results
            data.forEach(member => {
                const li = document.createElement('li');
                li.textContent = member.email;  // Display email in search results
                li.setAttribute('data-id', member.id);
                li.onclick = () => addMember(member.id, member.email);
                resultsContainer.appendChild(li);
            });
        });
}

// Add selected member to the form
function addMember(id, name) {
    const memberList = document.getElementById('selectedTeamMembers');
    if (!document.querySelector(`#selectedTeamMembers [data-id="${id}"]`)) {
        const member = document.createElement('li');
        member.textContent = name;
        member.setAttribute('data-id', id);
        memberList.appendChild(member);
        updateTeamMemberIds();
    }
}

// Update hidden input field with selected team member IDs
function updateTeamMemberIds() {
    const memberList = document.getElementById('selectedTeamMembers');
    const memberIds = [];
    memberList.querySelectorAll('li').forEach(member => {
        memberIds.push(parseInt(member.getAttribute('data-id')));
    });
    document.getElementById('teamMemberIds').value = JSON.stringify(memberIds);
}

function resetForm() {
    document.getElementById('createProjectForm').reset();
    document.getElementById('selectedTeamMembers').innerHTML = '';
    document.getElementById('searchResults').innerHTML = '';
}

document.getElementById('createProjectForm').addEventListener('submit', function(event) {
    event.preventDefault(); // Prevent the default form submission

    // Perform client-side validation
    if (!this.checkValidity()) {
        this.reportValidity();
        return;
    }

    // Custom validation for start date and end date
    const startDate = new Date(document.getElementById('startDate').value);
    const endDate = new Date(document.getElementById('endDate').value);
    if (endDate <= startDate) {
        alert('End date must be after the start date.');
        document.getElementById('endDate').focus();
        return;
    }

    const formData = new FormData(this);
    const data = Object.fromEntries(formData.entries());

    // Parse the JSON string back to an array
    data.teamMemberIds = JSON.parse(data.teamMemberIds);

    fetch('/api/projects', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(data)
    })
        .then(response => {
            if (response.ok) {
                alert('Project created successfully!');
                resetForm();
            } else {
                response.json().then(data => {
                    alert(`Failed to create project: ${data.message}`);
                });
            }
        })
        .catch(error => {
            console.error('Error:', error);
            alert('An error occurred while creating the project.');
        });
});
