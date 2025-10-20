// Common JS utilities used by the static pages
// This file provides a small helper to populate the dashboard counts
// and can be extended with shared helpers used by employees/departments pages.

const apiBase = '/api';

async function initDashboardCounts() {
  try {
    // fetch employees (we request 1 item per page; API returns totalElements)
    const eRes = await fetch(`${apiBase}/employees?page=0&size=1`);
    if (eRes.ok) {
      const eData = await eRes.json();
      const el = document.getElementById('empCount');
      if (el) el.innerText = `${eData.totalElements || 0} employees`;
    }

    const dRes = await fetch(`${apiBase}/departments?page=0&size=1`);
    if (dRes.ok) {
      const dData = await dRes.json();
      const el = document.getElementById('deptCount');
      if (el) el.innerText = `${dData.totalElements || 0} departments`;
    }
  } catch (err) {
    console.error('Failed to load dashboard counts', err);
  }
}

// small helper to show an alert area (used by pages)
function showAlert(message, type = 'danger') {
  const area = document.getElementById('alertArea');
  if (!area) return;
  area.innerHTML = `<div class="alert alert-${type} alert-dismissible" role="alert">${message}<button type="button" class="btn-close" data-bs-dismiss="alert"></button></div>`;
}

// initialize when included
document.addEventListener('DOMContentLoaded', () => {
  initDashboardCounts();
});
