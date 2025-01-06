package io.github.game;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

public class Map {
    private TiledMap tiledMap;
    private OrthogonalTiledMapRenderer mapRenderer;
    private TmxMapLoader mapLoader;

    public Map(String mapFilePath) {
        mapLoader = new TmxMapLoader();
        tiledMap = mapLoader.load(mapFilePath);
        mapRenderer = new OrthogonalTiledMapRenderer(tiledMap);

        // Define as camadas iniciais
        updateLayerVisibility(5); // Renderizar apenas as primeiras 5 camadas
    }

    public void render(OrthographicCamera camera) {
        mapRenderer.setView(camera);
        mapRenderer.render();
    }

    /**
     * Atualiza a visibilidade das camadas.
     * 
     * @param visibleLayersCount Quantidade de camadas a serem deixadas visíveis.
     */
    public void updateLayerVisibility(int visibleLayersCount) {
        for (int i = 0; i < tiledMap.getLayers().getCount(); i++) {
            MapLayer layer = tiledMap.getLayers().get(i);
            if (i < visibleLayersCount) {
                layer.setVisible(true); // Deixa visíveis as camadas até o limite especificado
            } else {
                layer.setVisible(false); // Torna invisíveis as camadas acima do limite
            }
        }
    }

    /**
     * Ativa uma camada adicional além da camada 5.
     * 
     * @param index Índice da camada a ser ativada.
     */
    public void activateLayer(int index) {
        if (index >= 5 && index < tiledMap.getLayers().getCount()) {
            MapLayer layer = tiledMap.getLayers().get(index);
            if (layer != null) {
                layer.setVisible(true); // Ativa a camada específica
            }
        }
    }

    public void dispose() {
        tiledMap.dispose();
        mapRenderer.dispose();
    }
}
