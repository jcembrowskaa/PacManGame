public class Main {
    public static void main(String[] args) {
        menuFrame menuFrame = new menuFrame();
    }
}

// apple-Pacman przez 7 sekund może zjadać duchy (zamiast tracić życie przy kontakcie).
// cherry Wszystkie duchy zostają zatrzymane (zamrożone) na 5 sekund — nie poruszają się.
//star Pacman porusza się szybciej — jego prędkość zwiększa się o 40% na 7 sekund.
//blueberry przez 7 sekund punkty zdobywane przez Pacmana są potrojone (x3).
//strawberry wszystkie duchy natychmiast wracają na swoją pozycję startową (spawn).