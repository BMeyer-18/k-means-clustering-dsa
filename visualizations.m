clear;
data = load("src\main\resources\data.csv");
x = data(:,1); y = data(:,2);

sizes = load("src\main\resources\sizes.csv");
indices = [0; cumsum(sizes)];

figure;
for i = 1:length(indices)-1
    plot(x(indices(i)+1:indices(i+1)), y(indices(i)+1:indices(i+1)), '.', MarkerSize=20); hold on
end
axis equal
title('Point Distribution')
exportgraphics(gca, "plot_example.png")