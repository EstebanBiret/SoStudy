document.addEventListener('DOMContentLoaded', function() {
    // Gérer l'affichage/masquage des réponses
    document.querySelectorAll('.toggle-replies-btn button').forEach(button => {
        button.addEventListener('click', function() {
            const container = this.closest('.comment').querySelector('.replies-container');
            const isHidden = container.style.display === 'none';
            
            container.style.display = isHidden ? 'block' : 'none';
            
            // Mettre à jour le texte du bouton
            const currentText = this.textContent;
            const newText = isHidden ? currentText.replace('Afficher', 'Masquer') : currentText.replace('Masquer', 'Afficher');
            this.textContent = newText;
        });
    });
});