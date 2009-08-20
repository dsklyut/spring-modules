package org.springmodules.jcr.jackrabbit.ocm;

import org.apache.jackrabbit.ocm.mapper.impl.digester.DigesterMapperImpl;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Extension of DigesterMapperImpl to allow use of Spring Resource abstraction for configuration.
 *
 * TODO: this is piss poor implementation and need to be refactored.  Just trying to get tests working.
 * User: dsklyut
 * Date: Aug 20, 2009
 * Time: 12:19:26 PM
 */
public class DigesterMapperWrapper extends DigesterMapperImpl {
    public DigesterMapperWrapper() {
    }

    public DigesterMapperWrapper(String xmlFile) {
        super(xmlFile);
    }

    public DigesterMapperWrapper(String[] files) {
        super(files);
    }

    public DigesterMapperWrapper(InputStream stream) {
        super(stream);
    }

    public DigesterMapperWrapper(InputStream[] streams) {
        super(streams);
    }

    public DigesterMapperWrapper(InputStream[] streams, boolean validate) {
        super(streams, validate);
    }

    public DigesterMapperWrapper(Resource resource) throws IOException {
        this(resource.getInputStream());
    }

    public DigesterMapperWrapper(Resource[] resources) throws IOException {
        this(convertToInputStream(resources));
    }

    public DigesterMapperWrapper(Resource[] resources, boolean validate) throws IOException {
        this(convertToInputStream(resources), validate);
    }

    private static InputStream[] convertToInputStream(Resource[] resources) throws IOException {
        List<InputStream> inputStreams = new ArrayList<InputStream>();
        for (Resource r : resources) {
            inputStreams.add(r.getInputStream());
        }
        return inputStreams.toArray(new InputStream[inputStreams.size()]);
    }

}
