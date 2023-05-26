import java.util.ArrayList;

public class Question_test {
    public static void main(String[] args) {

        ArrayList<String> choix_reponse1 = new ArrayList<String>();
                choix_reponse1.add("Italie");
                choix_reponse1.add("Grece");
                choix_reponse1.add("Espagne");
                choix_reponse1.add("Portugal");
                Question question1 = new Question("Dans quel pays se trouve le Colisée de Rome ?", choix_reponse1, 1,
                                "Italie");

                ArrayList<String> choix_reponse2 = new ArrayList<String>();
                choix_reponse2.add("Michel-Ange");
                choix_reponse2.add("Leonardo da Vinci");
                choix_reponse2.add("Pablo Picasso");
                choix_reponse2.add("Vincent van Gogh");
                Question question2 = new Question("Qui a peint la Joconde ?", choix_reponse2, 2, "Leonardo da Vinci");

                ArrayList<String> choix_reponse3 = new ArrayList<String>();
                choix_reponse3.add("Bresil");
                choix_reponse3.add("Argentine");
                choix_reponse3.add("Perou");
                choix_reponse3.add("Colombie");
                Question question3 = new Question("Quel est le plus grand pays d'Amérique du Sud ?", choix_reponse3, 3,
                                "Brésil");

                ArrayList<String> choix_reponse4 = new ArrayList<String>();
                choix_reponse4.add("Gustave Flaubert");
                choix_reponse4.add("Victor Hugo");
                choix_reponse4.add("Emile Zola");
                choix_reponse4.add("Charles Baudelaire");
                Question question4 = new Question("Qui a écrit le roman Les Misérables ?", choix_reponse4, 1,
                                "Victor Hugo");

                ArrayList<String> choix_reponse5 = new ArrayList<String>();
                choix_reponse5.add("Barcelone");
                choix_reponse5.add("Seville");
                choix_reponse5.add("Valence");
                choix_reponse5.add("Madrid");
                Question question5 = new Question("Quelle est la capitale de l'Espagne ?", choix_reponse5, 2, "Madrid");

                ArrayList<String> choix_reponse6 = new ArrayList<String>();
                choix_reponse6.add("Ocean Pacifique");
                choix_reponse6.add("Ocean Atlantique");
                choix_reponse6.add("Ocean Indien");
                choix_reponse6.add("Ocean Antarctique");
                Question question6 = new Question("Quel est le plus grand océan du monde ?", choix_reponse6, 3,
                                "Ocean Pacifique");

                ArrayList<String> choix_reponse7 = new ArrayList<String>();
                choix_reponse7.add("Mercure");
                choix_reponse7.add("Saturne");
                choix_reponse7.add("Jupiter");
                choix_reponse7.add("Vénus");
                Question question7 = new Question("Quelle est la plus grande planète du système solaire ?",
                                choix_reponse7, 1,
                                "Jupiter");

                ArrayList<String> choix_reponse8 = new ArrayList<String>();
                choix_reponse8.add("Rembrandt");
                choix_reponse8.add("Johannes Vermeer");
                choix_reponse8.add("Vincent van Gogh");
                choix_reponse8.add("Hieronymus Bosch");
                Question question8 = new Question("Quel célèbre peintre hollandais a coupé son propre lobe d'oreille ?",
                                choix_reponse8, 2, "Vincent van Gogh");

                ArrayList<String> choix_reponse9 = new ArrayList<String>();
                choix_reponse9.add("Aldous Huxley");
                choix_reponse9.add("George Orwell");
                choix_reponse9.add("Ernest Hemingway");
                choix_reponse9.add("Ray Bradbury");
                Question question9 = new Question("Qui a écrit 1984 ?", choix_reponse9, 3, "George Orwell");

                ArrayList<String> choix_reponse10 = new ArrayList<String>();
                choix_reponse10.add("Sahara");
                choix_reponse10.add("Gobi");
                choix_reponse10.add("Atacama");
                choix_reponse10.add("Antarctique");
                Question question10 = new Question("Quel est le plus grand désert du monde ?", choix_reponse10, 2,
                                "Sahara");

                ArrayList<String> choix_reponse11 = new ArrayList<String>();
                choix_reponse11.add("Angleterre");
                choix_reponse11.add("France");
                choix_reponse11.add("Allemagne");
                choix_reponse11.add("Italie");
                Question question11 = new Question("Dans quel pays se trouve la tour Eiffel ?", choix_reponse11, 3,
                                "France");

                ArrayList<String> choix_reponse12 = new ArrayList<String>();
                choix_reponse12.add("Jules Verne");
                choix_reponse12.add("Victor Hugo");
                choix_reponse12.add("Alexandre Dumas");
                choix_reponse12.add("Gustave Flaubert");
                Question question12 = new Question("Qui a écrit Les Trois Mousquetaires ?", choix_reponse12, 2,
                                "Alexandre Dumas");

                ArrayList<String> choix_reponse13 = new ArrayList<String>();
                choix_reponse13.add("Elephant");
                choix_reponse13.add("Rhinocéros");
                choix_reponse13.add("Girafe");
                choix_reponse13.add("Hippopotame");
                Question question13 = new Question("Quel est le plus grand mammifère terrestre ?", choix_reponse13, 2,
                                "Elephant");

                ArrayList<String> choix_reponse14 = new ArrayList<String>();
                choix_reponse14.add("Inde");
                choix_reponse14.add("Pakistan");
                choix_reponse14.add("Bangladesh");
                choix_reponse14.add("Nepal");
                Question question14 = new Question("Dans quel pays se trouve le Taj Mahal ?", choix_reponse14, 3,
                                "Inde");

                ArrayList<String> choix_reponse15 = new ArrayList<String>();
                choix_reponse15.add("Antoine de Saint-Exupery");
                choix_reponse15.add("Albert Camus");
                choix_reponse15.add("Marcel Proust");
                choix_reponse15.add("Simone de Beauvoir");
                Question question15 = new Question("Qui a écrit Le Petit Prince ?", choix_reponse15, 2,
                                "Antoine de Saint-Exupery");

                ArrayList<String> choix_reponse16 = new ArrayList<String>();
                choix_reponse16.add("Mont Everest");
                choix_reponse16.add("Mont Kilimandjaro");
                choix_reponse16.add("Mont Denali");
                choix_reponse16.add("Mont Elbrouz");
                Question question16 = new Question("Quel est le plus haut sommet du monde ?", choix_reponse16, 1,
                                "Mont Everest");

                ArrayList<String> choix_reponse17 = new ArrayList<String>();
                choix_reponse17.add("Italie");
                choix_reponse17.add("Grèce");
                choix_reponse17.add("Espagne");
                choix_reponse17.add("Portugal");
                Question question17 = new Question("Dans quel pays se trouve le Colisée de Rome ?", choix_reponse17, 2,
                                "Italie");

                ArrayList<String> choix_reponse18 = new ArrayList<String>();
                choix_reponse18.add("Charlotte Brontë");
                choix_reponse18.add("Jane Austen");
                choix_reponse18.add("Emily Bronte");
                choix_reponse18.add("Louisa May Alcott");
                Question question18 = new Question("Qui a écrit Orgueil et Préjugés ?", choix_reponse18, 2,
                                "Jane Austen");

                ArrayList<String> choix_reponse19 = new ArrayList<String>();
                choix_reponse19.add("Dante Alighieri");
                choix_reponse19.add("Giovanni Boccaccio");
                choix_reponse19.add("Niccolo Machiavelli");
                choix_reponse19.add("Francesco Petrarca");
                Question question19 = new Question("Qui a écrit La Divine Comédie ?", choix_reponse19, 2,
                                "Dante Alighieri");

                ArrayList<String> choix_reponse20 = new ArrayList<String>();
                choix_reponse20.add("Wolfgang Amadeus Mozart");
                choix_reponse20.add("Ludwig van Beethoven");
                choix_reponse20.add("Johann Sebastian Bach");
                choix_reponse20.add("Franz Schubert");
                Question question20 = new Question("Quel célèbre compositeur autrichien a écrit La Flûte enchantée ?",
                                choix_reponse20, 3, "Wolfgang Amadeus Mozart");

                for (Question question : Question.chargerQuestions()) {
                        System.out.println(question);
                }
    }
}