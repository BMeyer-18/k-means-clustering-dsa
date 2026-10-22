function plot_data()
    data = table2array(readtable("src\main\resources\staggerData.csv"));
    staggers = data(:,1);
    rand_cost = data(:,2); rand_iter = data(:,3);
    plus_cost = data(:,4); plus_iter = data(:,5);

    figure;
    plot(staggers, rand_cost); hold on
    plot(staggers, plus_cost)
    title('Cost vs Stagger')
    xlabel('Maximum Stagger From Defined Centroid')
    ylabel('Average Cost per Point')
    legend('Random Centroids', 'KMeans++ Centroids')
    exportgraphics(gca, 'graphics/cost_plot.png')

    figure;
    plot(staggers, rand_iter); hold on
    plot(staggers, plus_iter)
    title('Iteration Count vs Stagger')
    xlabel('Maximum Stagger From Defined Centroid')
    ylabel('Number of Iterations')
    legend('Random Centroids', 'KMeans++ Centroids')
    exportgraphics(gca, 'graphics/iter_plot.png')
end