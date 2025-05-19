function toggleCommunityMembership(button) {
    const communityId = button.getAttribute("data-community-id");
    const isMember = button.getAttribute("data-is-member") === "true";
    const url = `/community/${isMember ? 'leave' : 'join'}/${communityId}`;

    fetch(url, {
        method: "POST"
    })
    .then(res => {
        if (res.ok) {
            //update button
            const newButtonHtml = `
                <a href="" class="btn" id="${isMember ? 'join' : 'leave'}"
                   data-community-id="${communityId}"
                   data-is-member="${!isMember}"
                   onclick="event.preventDefault(); toggleCommunityMembership(this)">
                    ${isMember ? 'Rejoindre' : 'Quitter'}
                </a>
                ${!isMember ? `
                    <a href="/community/${communityId}" class="btn" id="voir">
                        Voir la communauté
                    </a>
                ` : ''}
            `;
            button.outerHTML = newButtonHtml;

            //update member count
            const memberCount = document.querySelector(`span[data-community-id="${communityId}"]`);
            if (memberCount) {
                memberCount.textContent = parseInt(memberCount.textContent) + (isMember ? -1 : 1);
            }

            //remove voir button if user is not a member
            if (isMember) {
                const voirButton = document.querySelector(`.community-actions[data-community-id="${communityId}"]`).querySelector('.btn#voir');
                voirButton.remove();
            }

        } else {
            alert("Erreur lors de la mise à jour de l'appartenance à la communauté");
        }
    });
}
