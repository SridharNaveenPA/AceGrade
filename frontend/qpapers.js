// Study Resources JavaScript

// API Configuration
const API_BASE_URL = 'http://localhost:8080/api/qpaper';

// Global data storage
let subjectsData = [];
let notesData = [];

// Global variables
let currentFilters = {
    semester: '',
    department: '',
    regulation: ''
};
let currentSubject = '';
let filteredNotes = [];

// DOM elements
const semesterSelect = document.getElementById('semester');
const departmentSelect = document.getElementById('department');
const regulationSelect = document.getElementById('regulation');
const subjectsList = document.getElementById('subjects-list');
const notesGrid = document.getElementById('notes-grid');
const searchInput = document.getElementById('search-notes');
const addNotesModal = document.getElementById('add-notes-modal');
const addNotesForm = document.getElementById('add-notes-form');

// Initialize the page
document.addEventListener('DOMContentLoaded', function() {
    initializeEventListeners();
    loadInitialData();
});

// Event listeners
function initializeEventListeners() {
    // Filter dropdowns
    semesterSelect.addEventListener('change', handleFilterChange);
    departmentSelect.addEventListener('change', handleFilterChange);
    regulationSelect.addEventListener('change', handleFilterChange);
    
    // Search functionality
    searchInput.addEventListener('input', handleSearch);
    
    // Form submission
    addNotesForm.addEventListener('submit', handleAddNotes);
    
    // Modal close on outside click
    window.addEventListener('click', function(event) {
        if (event.target === addNotesModal) {
            closeAddNotesModal();
        }
    });
}

// Handle filter changes
function handleFilterChange() {
    currentFilters.semester = semesterSelect.value;
    currentFilters.department = departmentSelect.value;
    currentFilters.regulation = regulationSelect.value;
    
    // Reset subject selection when filters change
    currentSubject = '';
    
    updateSubjectsList();
    updateNotesGrid();
}

// Load initial data
async function loadInitialData() {
    try {
        await loadSubjects();
        updateSubjectsList();
        updateNotesGrid();
    } catch (error) {
        console.error('Error loading initial data:', error);
        showNotification('Error loading data. Please refresh the page.', 'error');
    }
}

// Load subjects from API
async function loadSubjects() {
    try {
        const params = new URLSearchParams();
        if (currentFilters.semester) params.append('semester', currentFilters.semester);
        if (currentFilters.department) params.append('department', currentFilters.department);
        if (currentFilters.regulation) params.append('regulation', currentFilters.regulation);
        
        const response = await fetch(`${API_BASE_URL}/subjects?${params}`);
        if (!response.ok) {
            throw new Error('Failed to load subjects');
        }
        
        subjectsData = await response.json();
    } catch (error) {
        console.error('Error loading subjects:', error);
        subjectsData = [];
    }
}

// Load study resources from API
async function loadStudyResources() {
    try {
        const params = new URLSearchParams();
        if (currentFilters.semester) params.append('semester', currentFilters.semester);
        if (currentFilters.department) params.append('department', currentFilters.department);
        if (currentFilters.regulation) params.append('regulation', currentFilters.regulation);
        if (currentSubject) params.append('subjectName', currentSubject);
        
        const searchTerm = searchInput.value.trim();
        if (searchTerm) params.append('searchTerm', `%${searchTerm}%`);
        
        const url = `${API_BASE_URL}?${params}`;
        console.log('Loading study resources from:', url);
        console.log('Current filters:', currentFilters);
        console.log('Current subject:', currentSubject);
        
        const response = await fetch(url);
        if (!response.ok) {
            const errorText = await response.text();
            console.error('API Error:', response.status, errorText);
            throw new Error(`Failed to load study resources: ${response.status} ${errorText}`);
        }
        
        notesData = await response.json();
        console.log('Loaded study resources:', notesData);
    } catch (error) {
        console.error('Error loading study resources:', error);
        notesData = [];
    }
}

// Update subjects list based on current filters
async function updateSubjectsList() {
    if (!currentFilters.semester || !currentFilters.department || !currentFilters.regulation) {
        subjectsList.innerHTML = '<p class="no-selection">Please select semester, department, and regulation to view subjects</p>';
        return;
    }
    
    await loadSubjects();
    
    if (subjectsData.length === 0) {
        subjectsList.innerHTML = '<p class="no-selection">No subjects available for the selected combination</p>';
        return;
    }
    
    subjectsList.innerHTML = subjectsData.map(subject => 
        `<div class="subject-item ${currentSubject === subject.name ? 'active' : ''}" onclick="selectSubject('${subject.name}')">
            ${subject.name}
        </div>`
    ).join('');
}

// Select a subject
function selectSubject(subject) {
    currentSubject = subject;
    updateSubjectsList();
    updateNotesGrid();
}

// Update notes grid based on current filters and search
async function updateNotesGrid() {
    await loadStudyResources();
    
    if (notesData.length === 0) {
        notesGrid.innerHTML = `
            <div class="no-notes">
                <i class="fas fa-book-open"></i>
                <p>No notes available. ${!currentFilters.semester || !currentFilters.department || !currentFilters.regulation ? 'Select filters to view study materials.' : 'Try selecting a different subject or check back later.'}</p>
            </div>
        `;
        return;
    }
    
    notesGrid.innerHTML = notesData.map(note => createNoteCard(note)).join('');
}

// Create note card HTML
function createNoteCard(note) {
    return `
        <div class="note-card" onclick="downloadNote(${note.id})">
            <div class="note-header">
                <h3 class="contributor-name">${note.contributorName}</h3>
                <div class="download-count">
                    <i class="fas fa-download"></i>
                    <span>${note.downloadCount}</span>
                </div>
            </div>
            <div class="notes-content">
                <h4>Notes Content</h4>
                <p class="notes-description">${note.description || 'Study notes'}</p>
            </div>
            <div class="note-actions">
                <button class="download-btn" onclick="event.stopPropagation(); downloadNote(${note.id})">
                    <i class="fas fa-download"></i>
                    Download
                </button>
            </div>
        </div>
    `;
}

// Handle search
function handleSearch() {
    updateNotesGrid();
}

// Download note
async function downloadNote(noteId) {
    try {
        const response = await fetch(`${API_BASE_URL}/download/${noteId}`);
        if (!response.ok) {
            throw new Error('Failed to download file');
        }
        
        // Get the filename from the response headers
        const contentDisposition = response.headers.get('Content-Disposition');
        const filename = contentDisposition 
            ? contentDisposition.split('filename=')[1].replace(/"/g, '')
            : 'study_resource.pdf';
        
        // Create blob and download
        const blob = await response.blob();
        const url = window.URL.createObjectURL(blob);
        const a = document.createElement('a');
        a.href = url;
        a.download = filename;
        document.body.appendChild(a);
        a.click();
        window.URL.revokeObjectURL(url);
        document.body.removeChild(a);
        
        // Update the notes grid to reflect new download count
        await updateNotesGrid();
        
        showNotification(`Downloaded ${filename} successfully!`, 'success');
        
    } catch (error) {
        console.error('Error downloading file:', error);
        showNotification('Failed to download file. Please try again.', 'error');
    }
}

// Open add notes modal
function openAddNotesModal() {
    addNotesModal.style.display = 'block';
    document.body.style.overflow = 'hidden';
}

// Close add notes modal
function closeAddNotesModal() {
    addNotesModal.style.display = 'none';
    document.body.style.overflow = 'auto';
    addNotesForm.reset();
}

// Handle add notes form submission
async function handleAddNotes(event) {
    event.preventDefault();
    
    const formData = new FormData();
    const fileInput = document.getElementById('pdf-file');
    const semester = document.getElementById('modal-semester').value;
    const department = document.getElementById('modal-department').value;
    const regulation = document.getElementById('modal-regulation').value;
    const subjectName = document.getElementById('subject-name').value;
    const contributorName = document.getElementById('contributor-name').value;
    const description = document.getElementById('notes-description').value;
    
    // Validate required fields
    if (!semester || !department || !regulation || !subjectName || !contributorName || !fileInput.files[0]) {
        showNotification('Please fill in all required fields and select a PDF file', 'error');
        return;
    }
    
    // Validate file type
    if (fileInput.files[0].type !== 'application/pdf') {
        showNotification('Please select a PDF file', 'error');
        return;
    }
    
    // Prepare form data
    formData.append('file', fileInput.files[0]);
    formData.append('subjectName', subjectName);
    formData.append('contributorName', contributorName);
    formData.append('description', description);
    formData.append('semester', semester);
    formData.append('department', department);
    formData.append('regulation', regulation);
    
    try {
        const response = await fetch(`${API_BASE_URL}`, {
            method: 'POST',
            body: formData
        });
        
        if (!response.ok) {
            const errorData = await response.json();
            throw new Error(errorData.message || 'Failed to upload notes');
        }
        
        const newNote = await response.json();
        
        // Close modal and reset form
        closeAddNotesModal();
        
        // Set the current subject to the uploaded subject
        currentSubject = newNote.subjectName;
        
        // Update the display
        await updateSubjectsList();
        await updateNotesGrid();
        
        // Show success message
        showNotification('Notes uploaded successfully!', 'success');
        
        console.log('New note added:', newNote);
        
    } catch (error) {
        console.error('Error uploading notes:', error);
        showNotification(`Failed to upload notes: ${error.message}`, 'error');
    }
}

// Show notification
function showNotification(message, type = 'info') {
    // Create notification element
    const notification = document.createElement('div');
    notification.className = `notification notification-${type}`;
    notification.textContent = message;
    
    // Style the notification
    notification.style.cssText = `
        position: fixed;
        top: 100px;
        right: 20px;
        background: ${type === 'success' ? '#4CAF50' : type === 'error' ? '#f44336' : '#2196F3'};
        color: white;
        padding: 15px 20px;
        border-radius: 10px;
        z-index: 3000;
        font-size: 14px;
        box-shadow: 0 4px 12px rgba(0,0,0,0.3);
        transform: translateX(100%);
        transition: transform 0.3s ease;
    `;
    
    document.body.appendChild(notification);
    
    // Animate in
    setTimeout(() => {
        notification.style.transform = 'translateX(0)';
    }, 100);
    
    // Remove after 3 seconds
    setTimeout(() => {
        notification.style.transform = 'translateX(100%)';
        setTimeout(() => {
            document.body.removeChild(notification);
        }, 300);
    }, 3000);
}

// Utility function to get file extension
function getFileExtension(filename) {
    return filename.split('.').pop().toLowerCase();
}

// Utility function to format file size
function formatFileSize(bytes) {
    if (bytes === 0) return '0 Bytes';
    const k = 1024;
    const sizes = ['Bytes', 'KB', 'MB', 'GB'];
    const i = Math.floor(Math.log(bytes) / Math.log(k));
    return parseFloat((bytes / Math.pow(k, i)).toFixed(2)) + ' ' + sizes[i];
}
