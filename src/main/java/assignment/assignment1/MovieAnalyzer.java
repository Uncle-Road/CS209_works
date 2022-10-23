package assignment.assignment1;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author zmt 11912725
 */

public class MovieAnalyzer {

    public static class Movie {
        //      Poster_Link - address
        String posterLink;
        //      Series_Title - Name of the movie
        String seriesTitle;
        //      Released_Year - Year at which that movie released
        int releasedYear;
        //      Certificate - Certificate earned by that movie
        String certificate;
        //        Runtime - Total runtime of the movie
        int runtime;
        //        Genre - Genre of the movie
        String genre;
        //        IMDB_Rating - Rating of the movie at IMDB site
        float imdbRating;
        //        Overview - mini story/ summary
        String overview;
        //        Meta_score - Score earned by the movie
        int metaScore;
        //        Director - Name of the Director
        String director;
        //        Star1,Star2,Star3,Star4 - Name of the Stars
        String star1;
        String star2;
        String star3;
        String star4;
        int noOfVotes;
        double gross;

        public Movie() {

        }

        public String getPosterLink() {
            return posterLink;
        }

        public String getSeriesTitle() {
            return seriesTitle;
        }

        public int getReleasedYear() {
            return releasedYear;
        }

        public String getCertificate() {
            return certificate;
        }

        public int getRuntime() {
            return runtime;
        }

        public String getGenre() {
            return genre;
        }

        public double getImdbRating() {
            return imdbRating;
        }

        public String getOverview() {
            return overview;
        }

        public int getMetaScore() {
            return metaScore;
        }

        public String getDirector() {
            return director;
        }

        public String getStar1() {
            return star1;
        }

        public String getStar2() {
            return star2;
        }

        public String getStar3() {
            return star3;
        }

        public String getStar4() {
            return star4;
        }

        public int getNoOfVotes() {
            return noOfVotes;
        }

        public double getGross() {
            return gross;
        }

        public void getAllInformation() {
            System.out.println(getPosterLink() + "*"
                    + getSeriesTitle() + "*"
                    + getReleasedYear() + "*"
                    + getCertificate() + "*"
                    + getRuntime() + "*"
                    + getGenre() + "*"
                    + getImdbRating() + "*"
                    + getOverview() + "*"
                    + getMetaScore() + "*"
                    + getDirector() + "*"
                    + getStar1() + "*" + getStar2() + "*" + getStar3() + "*" + getStar4() + "*"
                    + getNoOfVotes() + "*" + getGross() + "*"
            );
        }

        public Movie(String posterLink, String seriesTitle, String releasedYear, String Certificate, String Runtime, String Genre, String imdbRating, String Overview, String metaScore, String Director, String Star1, String Star2, String Star3, String Star4, String noOfVotes, String Gross) {
            this.posterLink = posterLink.strip();
            this.seriesTitle = seriesTitle.replace("\"", "").strip();
            this.releasedYear = Integer.parseInt(releasedYear);
            this.certificate = Certificate.strip();
            this.runtime = Integer.parseInt(Runtime.replace("min", "").strip());
            this.genre = Genre.strip();
            this.imdbRating = Float.parseFloat(imdbRating.strip());
            this.overview = Overview.charAt(0) == '\"' ? Overview.substring(1, Overview.length() - 1) : Overview.strip();
            this.metaScore = Integer.parseInt(metaScore.replace("", "1").strip());
            this.director = Director.strip();
            this.star1 = Star1.strip();
            this.star2 = Star2.strip();
            this.star3 = Star3.strip();
            this.star4 = Star4.strip();
            this.noOfVotes = Integer.parseInt(noOfVotes);
            this.gross = Double.parseDouble(Gross.replace(",", "").replace("\"", "").strip());
        }

        public Movie(String[] arr) {
//            System.out.println(Arrays.toString(arr));
            this.posterLink = arr[0].strip();
            this.seriesTitle = arr[1].replace("\"", "").strip();
            this.releasedYear = Integer.parseInt(arr[2].strip());
            this.certificate = arr[3].strip();
            this.runtime = Integer.parseInt(arr[4].replace("min", "").strip());
            this.genre = arr[5].strip();
            this.imdbRating = Float.parseFloat(arr[6].strip());
            this.overview = arr[7].charAt(0) == '\"' ? arr[7].substring(1, arr[7].length() - 1) : arr[7].strip();
            this.metaScore = Integer.parseInt(arr[8].replace("", "1").strip());
            this.director = arr[9].strip();
            this.star1 = arr[10].strip();
            this.star2 = arr[11].strip();
            this.star3 = arr[12].strip();
            this.star4 = arr[13].strip();
            this.noOfVotes = Integer.parseInt(arr[14].strip());
            this.gross = Double.parseDouble(arr[15].replace(",", "").replace("\"", "").strip());
        }
    }

    List<Movie> movieList = new ArrayList<>();

    //step 0 reading the dataset
    public MovieAnalyzer(String dataset_path) {
        try {
            Files.lines(Paths.get(dataset_path)).skip(1).forEach(a -> {
                if (a.endsWith(",")) {
                    a += "\"1\"";
                }
                String[] arr = a.strip().split(",(?=([^\\\"]*\\\"[^\\\"]*\\\")*[^\\\"]*$)", -1);
                Movie m = new Movie(arr);
                movieList.add(m);
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    //step 1 Movie count by year
    public Map<Integer, Integer> getMovieCountByYear() {
        //returns <year, count> map
        //sort

        Map<Integer, Integer> res = movieList.stream().collect(Collectors.groupingBy(Movie::getReleasedYear, Collectors.summingInt(m -> 1)));

        Map<Integer, Integer> ans = new TreeMap<>(
                new Comparator<Integer>() {
                    @Override
                    public int compare(Integer a1, Integer a2) {
                        return a2.compareTo(a1);
                    }
                }
        );
        Set<Integer> keySets = res.keySet();
        Iterator<Integer> iter = keySets.iterator();
        while (iter.hasNext()) {
            Integer key = iter.next();
            ans.put(key, res.get(key));
        }
        return ans;
    }

    //step 2 Movie count by genre
    public Map<String, Integer> getMovieCountByGenre() {
        Map<String, Integer> res = new HashMap<>();

        movieList.stream().forEach(a -> {
            String[] arr = a.getGenre().split(",");
            for (String i : arr) {
                String tmp = i.replace("\"", "").strip();
                if (res.containsKey(tmp)) {
                    res.put(tmp, res.get(tmp) + 1);
                } else {
                    res.putIfAbsent(tmp, 1);
                }

            }
        });

        Map<String, Integer> ans = res.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));

        return ans;
    }

    //step 3 Movie count by co-stars
    public Map<List<String>, Integer> getCoStarCount() {
        Map<List<String>, Integer> res = new HashMap<>();
        movieList.forEach(a -> {
            String s1 = a.getStar1();
            String s2 = a.getStar2();
            String s3 = a.getStar3();
            String s4 = a.getStar4();
            String[] arr = new String[]{s1, s2, s3, s4};
            Arrays.sort(arr);
            for (int i = 0; i < 3; i++) {
                for (int j = i + 1; j < 4; j++) {
                    List<String> tmp = new ArrayList<>();
                    tmp.add(arr[i]);
                    tmp.add(arr[j]);
                    if (res.containsKey(tmp)) {
                        res.put(tmp, res.get(tmp) + 1);
                    } else {
                        res.putIfAbsent(tmp, 1);
                    }
                }
            }
        });
        return res;
    }

    //step 4 Top movies
    public List<String> getTopMovies(int top_k, String by) {
        List<String> res = new ArrayList<>();

        if (Objects.equals(by, "runtime")) {
            res = movieList.stream().sorted((m1, m2) -> {
                if (m1.getRuntime() == m2.getRuntime()) {
                    return m1.getSeriesTitle().compareTo(m2.getSeriesTitle());
                } else {
                    return m2.getRuntime() - m1.getRuntime();
                }
            }).map(Movie::getSeriesTitle).limit(top_k).collect(Collectors.toList());

        } else if (Objects.equals(by, "overview")) {
            res = movieList.stream().sorted((m1, m2) -> {
                if (m1.getOverview().length() == m2.getOverview().length()) {
                    return m1.getSeriesTitle().compareTo(m2.getSeriesTitle());
                } else {
                    return m2.getOverview().length() - m1.getOverview().length();
                }
            }).map(Movie::getSeriesTitle).limit(top_k).collect(Collectors.toList());
            ;
        }
        return res;
    }

    //step 5 Top stars
    public List<String> getTopStars(int top_k, String by) {
        List<String> res = new ArrayList<>();
        Map<String, Double> rating_avg = new HashMap<>();
        Map<String, Double> rating_sum = new HashMap<>();
        Map<String, Integer> occur_num = new HashMap<>();
        Map<String, Double> gross_avg = new HashMap<>();
        Map<String, Double> gross_sum = new HashMap<>();
        if (Objects.equals(by, "rating")) {
            movieList.stream().forEach(a -> {
                String s1 = a.getStar1();
                String s2 = a.getStar2();
                String s3 = a.getStar3();
                String s4 = a.getStar4();
                String[] arr = new String[]{s1, s2, s3, s4};
                Arrays.sort(arr);
                for (String i : arr) {
                    if (rating_sum.containsKey(i)) {
                        rating_sum.put(i, rating_sum.get(i) + a.getImdbRating());
                    } else {
                        rating_sum.putIfAbsent(i, a.getImdbRating());
                    }
                    if (occur_num.containsKey(i)) {
                        occur_num.put(i, occur_num.get(i) + 1);
                    } else {
                        occur_num.putIfAbsent(i, 1);
                    }
                }
            });
            Set<String> nameList = occur_num.keySet();
            for (String i : nameList) {
                rating_avg.put(i, rating_sum.get(i) / occur_num.get(i));
            }
            rating_avg = rating_avg.entrySet().stream()
                    .sorted(Map.Entry.comparingByKey())
                    .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
            res = rating_avg.keySet().stream().limit(top_k).toList();
        } else if (Objects.equals(by, "gross")) {
            movieList.stream().forEach(a -> {
                if (a.getGross() != 1) {
                    String s1 = a.getStar1();
                    String s2 = a.getStar2();
                    String s3 = a.getStar3();
                    String s4 = a.getStar4();
                    String[] arr = new String[]{s1, s2, s3, s4};
                    Arrays.sort(arr);
                    for (String i : arr) {
                        if (gross_sum.containsKey(i)) {
                            gross_sum.put(i, gross_sum.get(i) + a.getGross());
                        } else {
                            gross_sum.putIfAbsent(i, a.getGross());
                        }
                        if (occur_num.containsKey(i)) {
                            occur_num.put(i, occur_num.get(i) + 1);
                        } else {
                            occur_num.putIfAbsent(i, 1);
                        }
                    }
                }
            });
            Set<String> nameList = occur_num.keySet();
            for (String i : nameList) {
                gross_avg.put(i, gross_sum.get(i) / occur_num.get(i));
            }
            gross_avg = gross_avg.entrySet().stream()
                    .sorted(Map.Entry.comparingByKey())
                    .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
            res = gross_avg.keySet().stream().limit(top_k).toList();
        }
        return res;
    }

    //step 6 Search movies
    public List<String> searchMovies(String genre, float min_rating, int max_runtime) {
        List<String> res = new ArrayList<>();
        movieList.stream().filter(s -> s.getRuntime() <= max_runtime).filter(s -> s.getImdbRating() >= min_rating).forEach(
                a -> {
                    String[] arr = a.getGenre().split(",");
                    for (String i : arr) {
                        String tmp = i.replace("\"", "").strip();
                        if (tmp.equals(genre)) {
                            res.add(a.getSeriesTitle());
                        }
                    }
                }
        );
//        res.stream().forEach(s -> System.out.println(s.toString()));
        List<String> ans = res.stream().sorted(Comparator.naturalOrder()).toList();
        ans.stream().forEach(s -> System.out.println(s.toString()));
        return ans;
    }

    public static void main(String[] args) throws URISyntaxException {
        System.out.println("===================");
        MovieAnalyzer m = new MovieAnalyzer("D:\\code\\java\\22F-CS209A-Labs\\src\\main\\resources\\imdb_top_500.csv");
        System.out.println("-------------------");
        System.out.println(m.movieList.size());
        m.movieList.forEach(a -> {
            a.getAllInformation();
        });
    }
}