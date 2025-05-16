function openModal() {
    const selectedCheckboxes = document.querySelectorAll('.checkbox:checked');
    const selectedIds = Array.from(selectedCheckboxes).map(cb => cb.value);

    document.getElementById('selectedUsersInput').value = selectedIds.join(',');

    const channelNameWrapper = document.getElementById('channelNameWrapper');
    if (selectedIds.length > 1) {
        channelNameWrapper.style.display = 'block';
    } else {
        channelNameWrapper.style.display = 'none';
    }


    document.getElementById('myModal').style.display = 'flex';
}

function closeModal() {
    document.getElementById("myModal").style.display = "none";
}

function copyLink() {
    const input = document.querySelector('.share-link input');
    input.select();
    document.execCommand('copy');
}

window.onclick = function(event) {
    const modal = document.getElementById("myModal");
    if (event.target === modal) {
        modal.style.display = "none";
    }
}